<?xml version="1.0" encoding="UTF-8"?>
<!--

    Copyright 2010-2012 smartics, Kronseder & Reiner GmbH

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

-->
<!--
  Copies the module element enclosed in some header and footer.
-->

<xsl:stylesheet  xmlns:pm="http://smartics.de/projectmetadata/1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  	<xsl:output
		   method="xml"
		   indent="yes"
		   omit-xml-declaration="no"
		   media-type="text/xml"/>


	<xsl:template match="/">
		<document>
			<properties>
				<title>Static Analysis XML</title>
			</properties>
			<body>
				<section name="Static Analysis XML">
					<p>
					  Contains static analysis reports.
					</p>
          <ol>
            <li><a href="http://checkstyle.sourceforge.net/">Checkstyle</a></li>
            <li><a href="http://pmd.sourceforge.net/">PMD</a></li>
            <li><a href="http://findbugs.sourceforge.net/">Findbugs</a></li>
            <li><a href="http://javancss.codehaus.org/">JavaNCSS</a></li>
            <li><a href="http://www.clarkware.com/software/JDepend.html">JDepend</a></li>
          </ol>
					<source><xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
			  		<xsl:apply-templates select="pm:projectMetaData" />
			  		<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
			    </source>
			  </section>
			</body>
		</document>
	</xsl:template>

  <xsl:template match="pm:projectMetaData">
    <xsl:copy-of select="."/>
  </xsl:template>
</xsl:stylesheet>
