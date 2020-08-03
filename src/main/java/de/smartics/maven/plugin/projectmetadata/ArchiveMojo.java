/*
 * Copyright 2010-2020 smartics, Kronseder & Reiner GmbH
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

import org.apache.maven.archiver.MavenArchiveConfiguration;
import org.apache.maven.archiver.MavenArchiver;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.DefaultMavenProjectHelper;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.archiver.Archiver;
import org.codehaus.plexus.archiver.jar.JarArchiver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Collects selected project files and compiles them into a JAR file that is
 * attached to the build.
 *
 * @since 1.0
 * @description Collects selected project files and compiles them into a JAR
 *              file that is attached to the build.
 */
@Mojo(name = "archive", threadSafe = true, requiresProject = true,
    defaultPhase = LifecyclePhase.PACKAGE)
public class ArchiveMojo extends AbstractMojo {
  // ********************************* Fields *********************************

  // --- constants ------------------------------------------------------------

  // --- members --------------------------------------------------------------

  // ... Mojo infrastructure ..................................................

  /**
   * The Maven project.
   *
   * @since 1.0
   */
  @Component
  private MavenProject project;

  /**
   * The Maven session.
   *
   * @since 1.0
   */
  @Component
  private MavenSession session;

  /**
   * The client Jar archiver.
   *
   * @since 1.0
   */
  @Component(role = Archiver.class, hint = "jar")
  private JarArchiver jarArchiver;

  /**
   * The archive configuration to use. See
   * <a href="http://maven.apache.org/shared/maven-archiver/index.html">Maven
   * Archiver Reference</a>.
   *
   * @since 1.0
   */
  @Parameter
  private final MavenArchiveConfiguration archive =
      new MavenArchiveConfiguration();

  /**
   * The list of descriptor names to include from the standard folder. Choose
   * from
   * <ul>
   * <li>api</li>
   * <li>artifact</li>
   * <li>marker</li>
   * <li>static-analysis</li>
   * <li>test</li>
   * </ul>
   * Per default all reports are activated.
   *
   * @since 1.0
   */
  @Parameter
  private List<String> standardDescriptorNames;

  /**
   * The list of descriptor names to include from other locations. Please note
   * that only files are supported.
   *
   * @since 1.0
   */
  @Parameter
  private List<File> additionalDescriptorFiles;

  // ... Standard properties ..................................................

  /**
   * A simple flag to skip the generation of the XML files. If set on the
   * command line use <code>-Dprojectmetadata.skip</code>.
   *
   * @since 1.0
   */
  @Parameter(property = "projectmetadata.skip", defaultValue = "false")
  private boolean skip;

  // ... Specific properties ..................................................

  /**
   * Specifies whether to attach the generated artifact to the project helper.
   *
   * @since 1.0
   */
  @Parameter(property = "attach", defaultValue = "true")
  private boolean attach;

  /**
   * The classifier to use to generate the attached artifact.
   *
   * @since 1.0
   */
  @Parameter(defaultValue = "project")
  private String classifier;

  // ****************************** Initializer *******************************

  // ****************************** Constructors ******************************

  // ****************************** Inner Classes *****************************

  // ********************************* Methods ********************************

  // --- init -----------------------------------------------------------------

  // --- get&set --------------------------------------------------------------

  // --- business -------------------------------------------------------------

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (!skip) {
      init();

      final File jarFile = createArtifact();
      if (this.attach) {
        final MavenProjectHelper helper = new DefaultMavenProjectHelper();
        helper.attachArtifact(project, jarFile, classifier);
      }
    } else {
      getLog().info("Skipping projectmetadata plugin since skip=true.");
    }
  }

  private void init() {
    if (standardDescriptorNames == null) {
      standardDescriptorNames = new ArrayList<String>();
      standardDescriptorNames.add("marker");
      standardDescriptorNames.add("static-analysis");
      standardDescriptorNames.add("test");
      standardDescriptorNames.add("artifact");
      standardDescriptorNames.add("api");
      // TODO
    }
  }

  private File createArtifact() throws MojoExecutionException {
    final String finalName =
        project.getBuild().getFinalName() + '-' + classifier + ".jar";
    final File jarFile = new File(project.getBuild().getDirectory(), finalName);
    final MavenArchiver clientArchiver = new MavenArchiver();
    clientArchiver.setArchiver(jarArchiver);
    clientArchiver.setOutputFile(jarFile);

    try {
      final ArchiveCreator creator =
          new ArchiveCreator(project.getBasedir(), new DescriptorSet(
              standardDescriptorNames, additionalDescriptorFiles));
      creator.addProjectMetaDataFiles(clientArchiver.getArchiver());
      clientArchiver.createArchive(session, project, archive);
      return jarFile;
    } catch (final Exception e) {
      throw new MojoExecutionException(
          "There was a problem creating the project meta data archive: "
              + e.getMessage(),
          e);
    }
  }

  // --- object basics --------------------------------------------------------

}
