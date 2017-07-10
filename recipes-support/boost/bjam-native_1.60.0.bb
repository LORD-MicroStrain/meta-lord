include boost-${PV}.inc

SUMMARY = "Portable Boost.Jam build tool for boost"
SECTION = "devel"

LIC_FILES_CHKSUM = "file://../../LICENSE_1_0.txt;md5=e4224ccaecb14d942c71d31bef20d78c"

inherit native

S = "${WORKDIR}/${BOOST_P}/tools/build"

do_compile() {
    ./bootstrap.sh --with-toolset=gcc
}

do_install() {
    install -d ${D}${bindir}/

    ./b2 install --ignore-config --prefix=${D}${exec_prefix}
}
