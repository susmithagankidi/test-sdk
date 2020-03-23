# bom-update-stream

A little sample of the problem we've been having with the latest AWS Java SDK code.

We upgraded from version 2.5.10 to 2.10.81, and can no longer use the core Java class `java.util.zip.ZipInputStream`.

It can now only successfully unzip the first file whereas previously it could unzip multiple files. 
This seems to be because the S3Client.putObject call is resulting in the closing of the ZipInputStream.

#### configuration
Handler should be
`uk.gov.metoffice.swfhim.UnzippingHandler::handleRequest`
and runtime should be Java 11.