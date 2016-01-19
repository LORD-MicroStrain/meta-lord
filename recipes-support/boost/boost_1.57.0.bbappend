BOOST_LIBS += " \
    timer \
    log \
    python \
"

DEPENDS += "python"
PYTHON_ROOT = "${STAGING_DIR_HOST}/${prefix}"
PYTHON_VERSION = "2.7"
