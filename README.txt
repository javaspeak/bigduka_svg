
The following directory is an example unzipped  zip file downloaded from
bigduka:

      bigduka_svg\test\import\edition_1_20120130_20120111_08_09_01

The application needs two command line arguments:

    C:\JOHN\workspace\bigduka_svg\test\import\edition_1_20120130_20120111_08_09_01
    C:\JOHN\workspace\bigduka_svg\bin

The first argument is the root directory of the expanded zip file with the
images and data from bigduk

The second argument is where to generate the svg files to.

In eclipse there are 2 source directories that need to be mounted:

    bigduka_svg\java\frameworksrc
    bigduka_svg\java\src

The lib_jd project also needs to be in the classpath

The starting class is:

    bigduka_svg\java\src\com\javaspeak\bigduka\svg\generator\Start.java