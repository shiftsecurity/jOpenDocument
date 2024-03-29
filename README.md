# jOpenDocument

[![](https://jitpack.io/v/naruoga/jOpenDocument.svg)](https://jitpack.io/#naruoga/jOpenDocument)
[![Build with JDK8](https://github.com/shiftsecurity/jOpenDocument/actions/workflows/build_JDK8.yml/badge.svg)](https://github.com/shiftsecurity/jOpenDocument/actions/workflows/build_JDK8.yml)
[![Build with JDK11](https://github.com/shiftsecurity/jOpenDocument/actions/workflows/build_JDK11.yml/badge.svg)](https://github.com/shiftsecurity/jOpenDocument/actions/workflows/build_JDK11.yml)

This project is a fork to modernize http://www.jopendocument.org/.

The goals are as follows:

- change the original package name to ours
- Migrate from Ant to Maven
- Update dependencies
- Support for ODF 1.3 extended
- Java 11 and later support

Original README is below:
```

To compile you need to put iText ( http://www.lowagie.com/iText/download.html )
and junit4 ( http://www.junit.org/ ) into the lib/ dir. Then just call ant version 1.7+
(if you want to generate the java5 version pass -Djre5.dir="C:\Program Files\Java\jre1.5.0_15")

Note that you have to compile using a jdk 6, since there's a bug in the version 5 (can't
throw a generic)

To validate XML (needed for JUnit tests) you need to download http://java.net/downloads/msv/releases/msv.20090415.zip and
put msv.jar, relaxngDatatype.jar, xsdlib.jar and isorelax.jar in the classpath.

Google docs
-----------
jOpenDocument created documents should be compatible but (at least for spreadsheets) Google only accepts lower case automatic style names.
It also only supports lengths in centimetres (at least for column widths) but this is now the default for TableStyle.getWriteUnit().
```
