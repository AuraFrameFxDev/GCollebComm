#!/usr/bin/env sh

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS=""

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

warn () {
    echo "$*"
}

die () {
    echo
    echo "$*"
    echo
    exit 1
}

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
case "`uname`" in
  CYGWIN* )
    cygwin=true
    ;;
  Darwin* )
    darwin=true
    ;;
  MINGW* )
    msys=true
    ;;
esac

# For Cygwin, ensure paths are in UNIX format before anything is touched.
if $cygwin ; then
    [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
fi

if [ -z "$JAVA_HOME" ] ; then
    if $darwin ; then
        if [ -x "/usr/libexec/java_home" ]; then
            JAVA_HOME=`/usr/libexec/java_home`
        fi
    fi
fi

if [ -z "$JAVA_HOME" ] ; then
    die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH."
fi

CLASSPATH=

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME"  ] ; then
    JAVA_HOME=`echo "$JAVA_HOME" | sed 's/\\/\//g'`
    JAVA_CMD="$JAVA_HOME/bin/java"
    if [ ! -x "$JAVA_CMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME"
    fi
else
    JAVA_CMD="java"
fi

# Increase the maximum file descriptors if we can.
if [ "$cygwin" = "false" ] && [ "$msys" = "false" ] ; then
    MAX_FD_LIMIT=`ulimit -H -n`
    if [ $? -eq 0 ] ; then
        if [ "$MAX_FD_LIMIT" != "unlimited" ] ; then
            ulimit -n $MAX_FD_LIMIT
        fi
    fi
fi

# Escape application args
save_args() {
    for arg
    do
        printf '%s\n' "$arg"
    done
}

APP_ARGS=`save_args "$@"`

exec "$JAVA_CMD" $DEFAULT_JVM_OPTS -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"