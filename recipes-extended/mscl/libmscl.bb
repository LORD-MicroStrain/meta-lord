DESCRIPTION = "Library for accessing LORD MicroStrain sensors"

GIT_VERSION = "196f8ee6263bd33dfd7b94d1ab22ce3d7e986311"
PV = "git+${GIT_VERSION}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=021681b140003ff29ad66e1706b71009"

inherit bjam python-dir

PACKAGES += "${PN}-py"


DEPENDS += " \
	boost \
	python \
	swig-native \
"

RDEPENDS_${PN} += " \
	boost-thread \
	boost-system \
	boost-filesystem \
"

SRC_URI = "git://github.com/LORD-MicroStrain/MSCL.git;protocol=https;rev=${GIT_VERSION}"

BJAM_TARGETS = "MSCL//mscl MSCL//_mscl-dynamic"

S = "${WORKDIR}/git"

do_compile_prepend() {
	mkdir -p ${S}/build/swig-python
}

do_install_append() {
	${BJAM} stage_dynamic release

	install -d ${D}${libdir}
	#install -m 0600 ${S}/Output/lib/libmscl.so ${D}${libdir}
	install -m 0600 ${S}/Output/C++/Release/libmscl.so ${D}${libdir}/libmscl.so.1.0
	ln -s libmscl.so.1.0 ${D}${libdir}/libmscl.so.1
	ln -s libmscl.so.1.0 ${D}${libdir}/libmscl.so

	install -d ${D}${PYTHON_SITEPACKAGES_DIR}
	cp ${S}/Output/Python/_mscl-dynamic.so ${S}/Output/Python/_mscl.so
	install -m 0600 ${S}/Output/Python/_mscl.so ${D}${PYTHON_SITEPACKAGES_DIR}
	install -m 0600 ${S}/Output/Python/*.py ${D}${PYTHON_SITEPACKAGES_DIR}
	
	install -d ${D}${includedir}
	cp -r --no-preserve=ownership ${S}/Output/include/* ${D}${includedir}
}

#FILES_${PN}-dev = "${includedir} ${libdir}/libmscl.so.1.0"
FILES_${PN}-dbg += "${PYTHON_SITEPACKAGES_DIR}/.debug"
FILES_${PN}-py += "${PYTHON_SITEPACKAGES_DIR}"
