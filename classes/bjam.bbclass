DEPENDS = "bjam-native"

#
# Left this in from the boost recipe because I thought it was funny
#

# Oh yippee, a new build system, it's sooo cooool I could eat my own
# foot.  inlining=on lets the compiler choose, I think.  At least this
# stuff is documented...
# NOTE: if you leave <debug-symbols>on then in a debug build the build sys
# objcopy will be invoked, and that won't work.  Building debug apparently
# requires hacking gcc-tools.jam
#
# Sometimes I wake up screaming.  Famous figures are gathered in the nightmare,
# Steve Bourne, Larry Wall, the whole of the ANSI C committee.  They're just
# standing there, waiting, but the truely terrifying thing is what they carry
# in their hands.  At first sight each seems to bear the same thing, but it is
# not so for the forms in their grasp are ever so slightly different one from
# the other.  Each is twisted in some grotesque way from the other to make each
# an unspeakable perversion impossible to perceive without the onset of madness.
# True insanity awaits anyone who perceives all of these horrors together.
#

BJAM_CXX = "`echo ${CXX} | sed 's/^\([^ ]*\).*/\1/'`"

BJAM_TARGETS ?= ""

BJAM_TOOLSET = "gcc-oe"

PYTHON_VERSION = "2.7"

def PrefixList(prefix, spaceList):
    return " ".join([prefix + item for item in spaceList.split()])
    
def EraseFromList(item, spaceList):
    try:
        l = spaceList.split()
        l.remove(item)
        return " ".join(l)
    except:
        # item not in spaceList
        return spaceList
    
BJAM_CFLAGS = "${@EraseFromList('-ggdb3', bb.data.getVar('CFLAGS',d,1))} -I${STAGING_INCDIR}/python${PYTHON_VERSION}"

BJAM = "BOOST_BUILD_USER_CONFIG=${S}/user-config.jam bjam --ignore-site-config toolset=${BJAM_TOOLSET} -d+2 ${PARALLEL_MAKE}"

bjam_do_configure() {
	echo 'using gcc : oe : ${CXX} : ${@PrefixList('<compileflags>', bb.data.getVar('BJAM_CFLAGS',d,1))} ${@PrefixList('<linkflags>', bb.data.getVar('LDFLAGS',d,1))} ;' > ${S}/user-config.jam
	echo "using python : ${PYTHON_BASEVERSION} : : ${STAGING_INCDIR}/python${PYTHON_BASEVERSION} ;" >> ${S}/user-config.jam
	base_do_configure
}

bjam_do_compile() {
	set -ex
	${BJAM} ${BJAM_TARGETS} release hardcode-dll-paths=false
}

#
# You get to write your own install script!!!
#

EXPORT_FUNCTIONS do_compile do_configure

