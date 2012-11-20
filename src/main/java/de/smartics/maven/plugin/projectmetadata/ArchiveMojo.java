/*
 * Copyright 2010-2012 smartics, Kronseder & Reiner GmbH
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
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.archiver.MavenArchiveConfiguration;
import org.apache.maven.archiver.MavenArchiver;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.DefaultMavenProjectHelper;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.archiver.jar.JarArchiver;

import de.smartics.maven.util.LoggingUtils;

/**
 * Collects the project files and collects them to a JAR file that is attached
 * to the build.
 *
 * @goal archive
 * @phase package
 * @requiresProject
 * @description Collects the project files and collects them to a JAR file that
 *              is attached to the build.
 * @author <a href="mailto:robert.reiner@smartics.de">Robert Reiner</a>
 * @version $Revision:591 $
 */
public class ArchiveMojo extends AbstractMojo
{
  // ********************************* Fields *********************************

  // --- constants ------------------------------------------------------------

  // --- members --------------------------------------------------------------

  // ... Mojo infrastructure ..................................................

  /**
   * The Maven project.
   *
   * @parameter expression="${project}"
   * @required
   * @readonly
   * @since 1.0
   */
  private MavenProject project;

  /**
   * The client Jar archiver.
   *
   * @component role="org.codehaus.plexus.archiver.Archiver" roleHint="jar"
   * @since 1.0
   */
  private JarArchiver jarArchiver;

  /**
   * The archive configuration to use. See <a
   * href="http://maven.apache.org/shared/maven-archiver/index.html">Maven
   * Archiver Reference</a>.
   *
   * @parameter
   * @since 1.0
   */
  private final MavenArchiveConfiguration archive =
      new MavenArchiveConfiguration();

  /**
   * The list of descriptor names to include from the standard folder. Choose
   * from
   * <ul>
   * <li>marker</li>
   * <li>static-analysis</li>
   * <li>test</li>
   * <li>artifact</li>
   * </ul>
   * Per default all reports are activated.
   *
   * @parameter
   * @since 1.0
   */
  private List<String> standardDescriptorNames;

  /**
   * The list of descriptor names to include from other locations. Please note
   * that currently only files are supported. Only absolute file names are
   * supported. Use <tt>${basedir}</tt> to refer to files within the project
   * folder (which is recommended).
   *
   * @parameter
   * @since 1.0
   */
  private List<File> additionalDescriptorFiles;

  // ... Standard properties ..................................................

  /**
   * A simple flag to skip the generation of the XML files. If set on the
   * command line use <code>-Dprojectmetadata.skip</code>.
   *
   * @parameter expression="${projectmetadata.skip}" default-value="false"
   * @since 1.0
   */
  private boolean skip;

  /**
   * Specifies the log level used for this plugin.
   * <p>
   * Allowed values are <code>SEVERE</code>, <code>WARNING</code>,
   * <code>INFO</code> and <code>FINEST</code>.
   * </p>
   *
   * @parameter expression="${projectmetadata.logLevel}"
   * @since 1.0
   */
  private String logLevel;

  // ... Specific properties ..................................................

  /**
   * Specifies whether to attach the generated artifact to the project helper.
   *
   * @parameter expression="${attach}" default-value="true"
   * @since 1.0
   */
  private boolean attach;

  /**
   * The classifier to use to generate the attached artifact.
   *
   * @parameter default-value="project"
   * @since 1.0
   */
  private String classifier;

  // ****************************** Initializer *******************************

  // ****************************** Constructors ******************************

  // ****************************** Inner Classes *****************************

  // ********************************* Methods ********************************

  // --- init -----------------------------------------------------------------

  // --- get&set --------------------------------------------------------------

  // --- business -------------------------------------------------------------

  /**
   * {@inheritDoc}
   *
   * @see org.apache.maven.plugin.AbstractMojo#execute()
   */
  public void execute() throws MojoExecutionException, MojoFailureException
  {
    if (!skip)
    {
      init();

      final File jarFile = createArtifact();
      if (this.attach)
      {
        final MavenProjectHelper helper = new DefaultMavenProjectHelper();
        helper.attachArtifact(project, jarFile, classifier);
      }
    }
    else
    {
      getLog().info("Skipping projectmetadata plugin since skip=true.");
    }
  }

  private void init()
  {
    LoggingUtils.configureLogger(getLog(), logLevel);

    if (standardDescriptorNames == null)
    {
      standardDescriptorNames = new ArrayList<String>();
      standardDescriptorNames.add("marker");
      standardDescriptorNames.add("static-analysis");
      standardDescriptorNames.add("test");
      standardDescriptorNames.add("artifact");
      // TODO
    }
  }

  private File createArtifact() throws MojoExecutionException
  {
    final String finalName =
        project.getBuild().getFinalName() + '-' + classifier + ".jar";
    final File jarFile = new File(project.getBuild().getDirectory(), finalName);
    final MavenArchiver clientArchiver = new MavenArchiver();
    clientArchiver.setArchiver(jarArchiver);
    clientArchiver.setOutputFile(jarFile);

    try
    {
      final ArchiveCreator creator =
          new ArchiveCreator(project.getBasedir(), new DescriptorSet(
              standardDescriptorNames, additionalDescriptorFiles));
      creator.addProjectMetaDataFiles(clientArchiver.getArchiver());
      clientArchiver.createArchive(project, archive);
      return jarFile;
    }
    catch (final Exception e)
    {
      throw new MojoExecutionException(
          "There was a problem creating the project meta data archive: "
              + e.getMessage(), e);
    }
  }

  // --- object basics --------------------------------------------------------

}
