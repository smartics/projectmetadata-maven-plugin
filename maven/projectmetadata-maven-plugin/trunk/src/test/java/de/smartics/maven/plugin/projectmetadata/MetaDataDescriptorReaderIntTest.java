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

import java.io.IOException;

import org.junit.Test;

/**
 * Tests the correctness of the standard descriptors.
 * <p>
 * We do not test the content, since this may change. We simply want to make
 * sure that they are parsable.
 * </p>
 *
 * @author <a href="mailto:robert.reiner@smartics.de">Robert Reiner</a>
 * @version $Revision:591 $
 */
public class MetaDataDescriptorReaderIntTest
{
  // ********************************* Fields *********************************

  // --- constants ------------------------------------------------------------

  // --- members --------------------------------------------------------------

  // ****************************** Inner Classes *****************************

  // ********************************* Methods ********************************

  // --- prepare --------------------------------------------------------------

  // --- helper ---------------------------------------------------------------

  // --- tests ----------------------------------------------------------------

  @Test(expected = IOException.class)
  public void unknownDescriptor() throws IOException
  {
    final MetaDataDescriptorReader uut =
        ProjectTestUtils.createStandardReader("xxx");
    uut.readMetaDataDescriptors();
  }

  @Test
  public void markerDescriptor() throws IOException
  {
    final MetaDataDescriptorReader uut =
        ProjectTestUtils.createStandardReader("marker");
    uut.readMetaDataDescriptors();
  }

  @Test
  public void staticAnalysisDescriptor() throws IOException
  {
    final MetaDataDescriptorReader uut =
        ProjectTestUtils.createStandardReader("static-analysis");
    uut.readMetaDataDescriptors();
  }

  @Test
  public void testDescriptor() throws IOException
  {
    final MetaDataDescriptorReader uut =
        ProjectTestUtils.createStandardReader("test");
    uut.readMetaDataDescriptors();
  }
}
