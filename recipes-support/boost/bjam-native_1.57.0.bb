include recipes-support/boost/boost-${PV}.inc

SUMMARY = "Portable Boost.Jam build tool for boost"
SECTION = "devel"

inherit native

do_compile() {
    cd tools/build
    ./bootstrap.sh --with-toolset=gcc
    cd ../..
}

do_install() {
    cd tools/build
    ./b2 install --ignore-config --prefix=${D}${exec_prefix}
    cd ../..
}
