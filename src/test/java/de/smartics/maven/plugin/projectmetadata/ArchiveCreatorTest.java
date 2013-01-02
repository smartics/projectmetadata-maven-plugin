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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.plexus.archiver.ArchiverException;
import org.codehaus.plexus.archiver.jar.JarArchiver;
import org.junit.Ignore;
import org.junit.Test;

import de.smartics.util.test.io.FileTestUtils;

/**
 * Tests {@link ArchiveCreator}.
 *
 * @author <a href="mailto:robert.reiner@smartics.de">Robert Reiner</a>
 * @version $Revision:591 $
 */
public class ArchiveCreatorTest
{
  // ********************************* Fields *********************************

  // --- constants ------------------------------------------------------------

  // --- members --------------------------------------------------------------

  // ****************************** Inner Classes *****************************

  /**
   * The mock checks during the test run that no destination file is added more
   * than once. At the end of the test, the added files can be accessed.
   */
  private static final class MockJarArchiver extends JarArchiver
  {
    /**
     * The track of added files.
     */
    private final Map<String, File> addedFiles =
        new LinkedHashMap<String, File>();

    /**
     * {@inheritDoc}
     * <p>
     * The implementation simply added the added files to a memory hash table.
     * </p>
     *
     * @see org.codehaus.plexus.archiver.AbstractArchiver#addFile(java.io.File,
     *      java.lang.String)
     */
    @Override
    public void addFile(final File inputFile, final String destFileName)
      throws ArchiverException
    {
      if (addedFiles.containsKey(destFileName))
      {
        final File oldFile = addedFiles.get(destFileName);
        throw new IllegalStateException(
            "The destination file '" + destFileName
                + "' is added the second time! Already stored: "
                + oldFile.getAbsolutePath() + " / Current: "
                + inputFile.getAbsolutePath());
      }
      addedFiles.put(destFileName, inputFile);
    }

    /**
     * Returns the track of added files.
     *
     * @return the track of added files.
     */
    public Map<String, File> getAddedFiles()
    {
      return addedFiles;
    }

    /**
     * Checks if the expected file has been archived.
     *
     * @param expectedFileName the name of the expected file within the archive.
     */
    public void checkFileArchived(final String expectedFileName)
    {
      final File expectedFile = addedFiles.get(expectedFileName);
      assertNotNull("File '" + expectedFileName + "' not archived.",
          expectedFile);
    }
  }

  // ********************************* Methods ********************************

  // --- prepare --------------------------------------------------------------

  // --- helper ---------------------------------------------------------------

  private static DescriptorSet createTestSet(final String testSetName,
      final String descriptorName)
  {
    final String basePath = "archivecreator/" + testSetName;
    return ProjectTestUtils.createAdditionalTestSet(basePath, descriptorName);
  }

  private void runCheckstyleOnly(final DescriptorSet descriptorSet)
    throws IOException
  {
    final File projectBasedir =
        FileTestUtils.getFileFromResource("archivecreator/checkstyleonly");
    final ArchiveCreator uut =
        new ArchiveCreator(projectBasedir, descriptorSet);
    final MockJarArchiver mockArchiver = new MockJarArchiver();

    uut.addProjectMetaDataFiles(mockArchiver);

    final Map<String, File> addedFiles = mockArchiver.getAddedFiles();
    assertEquals(2, addedFiles.size());
    mockArchiver
        .checkFileArchived(ProjectTestUtils
            .normalizePath("code/static-analysis/checkstyle/checkstyle-checker.xml"));
    mockArchiver
        .checkFileArchived(ProjectTestUtils
            .normalizePath("code/static-analysis/checkstyle/checkstyle-result.xml"));
  }

  // --- tests ----------------------------------------------------------------

  @Test(expected = NullPointerException.class)
  public void constructorNullProjectBaseDir()
  {
    new ArchiveCreator(null, new DescriptorSet(null, null));
  }

  @Test(expected = NullPointerException.class)
  public void constructorNullDescriptorSet()
  {
    new ArchiveCreator(new File("x"), null);
  }

  @Test
  public void staticAnalysisWithOnlyCheckstyleFiles() throws IOException
  {
    final DescriptorSet descriptorSet =
        createTestSet("checkstyleonly", "checkstyle-only");
    runCheckstyleOnly(descriptorSet);
  }

  @Test
  public void staticAnalysisWithCheckstyleAndNoPmdFiles() throws IOException
  {
    final DescriptorSet descriptorSet =
        createTestSet("checkstyleonly", "checkstyle-and-no-others");
    runCheckstyleOnly(descriptorSet);
  }

  @Test
  @Ignore // FIXME: Check the test!
  public void singleFile() throws IOException
  {
    final DescriptorSet descriptorSet =
        createTestSet("singlefile", "single-file");
    final File projectBasedir =
        FileTestUtils.getFileFromResource("archivecreator/singlefile");
    final ArchiveCreator uut =
        new ArchiveCreator(projectBasedir, descriptorSet);
    final MockJarArchiver mockArchiver = new MockJarArchiver();

    uut.addProjectMetaDataFiles(mockArchiver);

    final Map<String, File> addedFiles = mockArchiver.getAddedFiles();
    assertEquals(1, addedFiles.size());
    mockArchiver.checkFileArchived(ProjectTestUtils
        .normalizePath("code/static-analysis/findbugs/findbugsXml.xml"));
  }
}
