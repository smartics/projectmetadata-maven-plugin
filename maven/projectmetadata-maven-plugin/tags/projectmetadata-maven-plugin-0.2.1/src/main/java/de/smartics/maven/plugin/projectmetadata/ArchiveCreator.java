/*
 * Copyright 2010-2013 smartics, Kronseder & Reiner GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.smartics.maven.plugin.projectmetadata;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.codehaus.plexus.archiver.ArchiverException;
import org.codehaus.plexus.archiver.jar.JarArchiver;
import org.codehaus.plexus.util.DirectoryScanner;

import de.smartics.maven.plugin.projectmetadata.bo.FilesDescriptor;
import de.smartics.maven.plugin.projectmetadata.bo.FilterDescriptor;
import de.smartics.maven.plugin.projectmetadata.bo.MetaDataDescriptor;

/**
 * Creates the archive and populates it with the meta data files described by
 * the descriptor set.
 *
 * @author <a href="mailto:robert.reiner@smartics.de">Robert Reiner</a>
 * @version $Revision:591 $
 */
class ArchiveCreator
{
  // ********************************* Fields *********************************

  // --- constants ------------------------------------------------------------

  /**
   * The project's base directory.
   */
  private final File projectBasedir;

  /**
   * The list of descriptor names to include from the standard and additional
   * folder.
   *
   * @parameter
   */
  private final DescriptorSet descriptorSet;

  // --- members --------------------------------------------------------------

  // ****************************** Initializer *******************************

  // ****************************** Constructors ******************************

  /**
   * Default constructor.
   *
   * @param projectBasedir the project's base directory.
   * @param descriptorSet the list of descriptor names to include from the
   *          standard and additional folder.
   * @throws NullPointerException if <code>projectBasedir</code> or
   *           <code>descriptorSet</code> is <code>null</code>.
   */
  ArchiveCreator(final File projectBasedir, final DescriptorSet descriptorSet)
    throws NullPointerException
  {
    checkArguments(projectBasedir, descriptorSet);
    this.projectBasedir = projectBasedir;
    this.descriptorSet = descriptorSet;
  }

  // ****************************** Inner Classes *****************************

  // ********************************* Methods ********************************

  // --- init -----------------------------------------------------------------

  private static void checkArguments(final File projectBasedir,
      final DescriptorSet descriptorSet)
  {
    if (projectBasedir == null)
    {
      throw new NullPointerException(
          "The file pointing to the base directory of the project must not be 'null'");
    }
    if (descriptorSet == null)
    {
      throw new NullPointerException(
          "The descriptor set pointing to the meta data to be included must not be 'null'");
    }
  }

  // --- get&set --------------------------------------------------------------

  // --- business -------------------------------------------------------------

  void addProjectMetaDataFiles(final JarArchiver archiver) throws IOException
  {
    final MetaDataDescriptorReader reader =
        new MetaDataDescriptorReader(descriptorSet);
    final List<MetaDataDescriptor> descriptors =
        reader.readMetaDataDescriptors();
    for (final MetaDataDescriptor descriptor : descriptors)
    {
      final FilesDescriptor files = descriptor.getFiles();
      final File ref =
          new File(this.projectBasedir, files.getProjectLocation());
      if (ref.isDirectory())
      {
        addFiles(archiver, descriptor);
      }
      else
      {
        addFile(archiver, descriptor, files, ref);
      }
    }
  }

  private void addFile(final JarArchiver archiver,
      final MetaDataDescriptor descriptor, final FilesDescriptor files,
      final File file) throws IOException
  {
    if (file.exists())
    {
      final String destFileName = files.getArchiveLocation();
      final String name = file.getName();
      addFileToArchive(archiver, descriptor, file,
          new File(destFileName, name).getPath());
    }
  }

  private void addFileToArchive(final JarArchiver archiver,
      final MetaDataDescriptor descriptor, final File file,
      final String destFileName) throws IOException
  {
    try
    {
      archiver.addFile(file, destFileName);
    }
    catch (final ArchiverException e)
    {
      final IOException ioe =
          new IOException("Cannot add meta data '" + descriptor.getName()
                          + "'.");
      ioe.initCause(e);
      throw ioe; // NOPMD: We added the cause in the line above.
    }
  }

  private void addFiles(final JarArchiver archiver,
      final MetaDataDescriptor descriptor) throws IOException
  {
    final DirectoryScanner scanner = createDirectoryScanner(descriptor);
    scanner.scan();
    for (final String includedFilePath : scanner.getIncludedFiles())
    {
      final File file = new File(scanner.getBasedir(), includedFilePath);
      final String destFileName = createDestFileName(descriptor, file);
      addFileToArchive(archiver, descriptor, file, destFileName);
    }
  }

  private String createDestFileName(final MetaDataDescriptor descriptor,
      final File file)
  {
    final String dest = descriptor.getFiles().getArchiveLocation();
    final String fileName = file.getName();
    final File destFile = new File(dest, fileName);
    return destFile.getPath();
  }

  private DirectoryScanner createDirectoryScanner(
      final MetaDataDescriptor descriptor)
  {
    final FilesDescriptor files = descriptor.getFiles();
    final DirectoryScanner scanner = new DirectoryScanner();
    final File basedir =
        new File(this.projectBasedir, files.getProjectLocation());
    scanner.setBasedir(basedir);
    addFilter(files, scanner);
    return scanner;
  }

  private void addFilter(final FilesDescriptor files,
      final DirectoryScanner scanner)
  {
    final FilterDescriptor filter = files.getFilter();
    if (filter != null)
    {
      final List<String> includes = filter.getIncludes();
      if (includes != null && !includes.isEmpty())
      {
        scanner.setIncludes(includes.toArray(new String[includes.size()]));
      }
      final List<String> excludes = filter.getExcludes();
      if (excludes != null && !excludes.isEmpty())
      {
        scanner.setExcludes(excludes.toArray(new String[excludes.size()]));
      }
    }
  }

  // --- object basics --------------------------------------------------------

}
