/*
 * Copyright 2010-2025 smartics, Kronseder & Reiner GmbH
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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import de.smartics.maven.plugin.projectmetadata.bo.FilesDescriptor;
import de.smartics.maven.plugin.projectmetadata.bo.FilterDescriptor;
import de.smartics.maven.plugin.projectmetadata.bo.MetaDataDescriptor;
import de.smartics.maven.plugin.projectmetadata.bo.ProjectMetaDataDescriptors;
import org.codehaus.plexus.util.IOUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads the configured project meta data descriptors.
 */
public class MetaDataDescriptorReader {
  // ********************************* Fields *********************************

  // --- constants ------------------------------------------------------------

  // --- members --------------------------------------------------------------

  /**
   * The list of descriptor names to include from the standard and additional
   * folder.
   */
  private final DescriptorSet descriptorSet;

  // ****************************** Initializer *******************************

  // ****************************** Constructors ******************************

  /**
   * Default constructor.
   *
   * @param descriptorSet the list of descriptor names to include from the
   *                      standard and additional folder.
   */
  public MetaDataDescriptorReader(final DescriptorSet descriptorSet) {
    this.descriptorSet = descriptorSet;
  }

  // ****************************** Inner Classes *****************************

  // ********************************* Methods ********************************

  // --- init -----------------------------------------------------------------

  // --- get&set --------------------------------------------------------------

  // --- business -------------------------------------------------------------

  /**
   * Reads the configured meta data descriptors.
   *
   * @return the descriptors read.
   * @throws IOException if any of the requested descriptors cannot be read.
   */
  public List<MetaDataDescriptor> readMetaDataDescriptors() throws IOException {
    final List<MetaDataDescriptor> metaDataList =
        new ArrayList<MetaDataDescriptor>();

    final XStream xstream = initStream();

    readStandardDescriptors(metaDataList, xstream);
    readAdditionalDescriptors(metaDataList, xstream);

    return metaDataList;
  }

  private XStream initStream() {
    final XStream xstream = new XStream();

    xstream.addPermission(NoTypePermission.NONE);
    xstream.addPermission(NullPermission.NULL);
    xstream.allowTypes(
        new Class[] {String.class, ProjectMetaDataDescriptors.class,
            MetaDataDescriptor.class, FilterDescriptor.class,
            FilesDescriptor.class});

    xstream.alias("projectMetaData", ProjectMetaDataDescriptors.class);
    xstream.addImplicitCollection(ProjectMetaDataDescriptors.class,
        "metaDataDescriptors");

    xstream.alias("metaData", MetaDataDescriptor.class);
    xstream.alias("name", String.class);
    xstream.alias("homepage", String.class);
    xstream.alias("description", String.class);
    xstream.alias("category", String.class);
    xstream.alias("subcategory", String.class);

    xstream.alias("files", FilesDescriptor.class);
    xstream.alias("projectLocation", String.class);
    xstream.alias("archiveLocation", String.class);

    xstream.alias("filter", FilterDescriptor.class);
    xstream.alias("include", String.class);
    xstream.alias("exclude", String.class);

    return xstream;
  }

  private void readStandardDescriptors(
      final List<MetaDataDescriptor> metaDataList, final XStream xstream)
          throws IOException {
    final List<String> standardDescriptorNames =
        descriptorSet.getStandardDescriptorNames();

    if (standardDescriptorNames != null) {
      for (final String name : standardDescriptorNames) {
        final String classPathFileName = "descriptors/" + name + ".xml";
        final InputStream in =
            getClass().getResourceAsStream(classPathFileName);
        if (in != null) {
          try {
            final ProjectMetaDataDescriptors project =
                (ProjectMetaDataDescriptors) xstream.fromXML(in);
            metaDataList.addAll(project.getMetaDataDescriptors());
          } finally {
            IOUtil.close(in);
          }
        } else {
          throw new IOException("Cannot read meta data descriptor file '" + name
              + "' from class path '" + classPathFileName + "'.");
        }
      }
    }
  }

  private void readAdditionalDescriptors(
      final List<MetaDataDescriptor> metaDataList, final XStream xstream)
          throws IOException {
    final List<File> additionalDescriptorFiles =
        descriptorSet.getAdditionalDescriptorFiles();

    if (additionalDescriptorFiles != null) {
      for (final File file : additionalDescriptorFiles) {
        InputStream in = null;
        try {
          in = new BufferedInputStream(new FileInputStream(file));
          final ProjectMetaDataDescriptors project =
              (ProjectMetaDataDescriptors) xstream.fromXML(in);
          final List<MetaDataDescriptor> descriptors =
              project.getMetaDataDescriptors();
          if (descriptors != null && !descriptors.isEmpty()) {
            metaDataList.addAll(descriptors);
          }
        } finally {
          IOUtil.close(in);
        }
      }
    }
  }

  // --- object basics --------------------------------------------------------

}
