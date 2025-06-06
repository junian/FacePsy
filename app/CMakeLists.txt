# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.
cmake_minimum_required(VERSION 3.5.1)

# Configure build library name.
set(TARGET_NAME native-lib)

# Build project shared lib
set(CMAKE_VERBOSE_MAKEFILE on)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fexceptions -std=c++11")

# TODO: Path to project: REPLACE WITH YOUR PATH!
# Choose 1) or 2) based on your OS
# 1) For macOS / Linux:
set(PROJECT_PATH "/Users/username/FacePsy")
# 2) For Windows:
# set(PROJECT_PATH "C:/Users/username/FacePsy")

# Configure import libs: MAKE SURE TO HAVE A 'cppLibs' DIRECTORY"
set(LIB_DIR ${CMAKE_SOURCE_DIR}/src/main/cppLibs)


# ------------------------------------------------------------------
# -- OPENCV
# ------------------------------------------------------------------
# TODO: Path to OpenCV: REPLACE WITH YOUR PATH!
# Choose 1) or 2) based on your OS
# 1) macOS / Linux
set(OPENCV_PATH "/Users/username/opencv4/")
# 2) Windows
# set(OPENCV_PATH "C:/Users/username/opencv4/")

# Make directories for Opencv
file(MAKE_DIRECTORY ${LIB_DIR}/opencv)
file(MAKE_DIRECTORY ${LIB_DIR}/opencv/${ANDROID_ABI})

add_library(lib_opencv SHARED IMPORTED)

# sets the location of the prebuilt opencv .so
set_target_properties( lib_opencv
                       PROPERTIES IMPORTED_LOCATION
                       ${LIB_DIR}/opencv/${ANDROID_ABI}/libopencv_java4.so )

include_directories(${OPENCV_PATH}/sdk/native/jni/include)

# ------------------------------------------------------------------


# ------------------------------------------------------------------
# -- DLIB
# ------------------------------------------------------------------
set(DLIB_PATH ${LIB_DIR}/dlib)

# Make directories for Dlib
file(MAKE_DIRECTORY ${LIB_DIR}/dlib)
file(MAKE_DIRECTORY ${DLIB_PATH}/include)
file(MAKE_DIRECTORY ${DLIB_PATH}/lib)
file(MAKE_DIRECTORY ${DLIB_PATH}/lib/${ANDROID_ABI})

add_library(dlib SHARED IMPORTED)

# sets the location of the prebuilt dlib .so
set_target_properties( dlib
                       PROPERTIES IMPORTED_LOCATION
                       ${DLIB_PATH}/lib/${ANDROID_ABI}/libdlib.so )

include_directories(${DLIB_PATH}/include)

# ------------------------------------------------------------------


# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             ${TARGET_NAME}

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/native-lib.cpp )


target_include_directories( ${TARGET_NAME} PRIVATE
                            ${CMAKE_SOURCE_DIR}/include
                            ${DLIB_PATH}/include )


# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       native-lib

                       lib_opencv
                       dlib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )