# Sets the minimum version of CMake required to build the native
# library. You should either keep the default value or only pass a
# value of 3.4.0 or lower.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds it for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             native-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             # Associated headers in the same location as their source
             # file are automatically included.
             src/main/cpp/native-lib.cpp )

include_directories(src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5)
include_directories(src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/shared)
include_directories(src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL)
include_directories(src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp)
include_directories(src/main/cpp/lp_solve_5.5.2.5_source/)
include_directories(src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL)

include_directories(src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/colamd)
include_directories(src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_solve)
include_directories(src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lpsolve55)

file(GLOB HEADERS2

    src/main/cpp/lp_solve_5.5.2.5_source/*.h
    src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/shared/*.h
    src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/*.h
    src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/*.h
    src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/colamd/*.h
    src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/*.h
    src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_solve/*.h
    src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lpsolve55/*.h
    src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/*.h
    )


file(GLOB_RECURSE HEADERS
    src/main/cpp/lp_solve_5.5.2.5_source/*.h
    )

    file(GLOB_RECURSE SOURCE
        src/main/cpp/lp_solve_5.5.2.5_source/*.c
        )
    message ("Headers are  :${HEADERS}")
    message ("Sources are  :${SOURCE}")

add_library( # Sets the name of the library.
             native-liblpsolve
             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             # Associated headers in the same location as their source
             # file are automatically included.
             src/main/cpp/lp_solve_5.5.2.5_source/lpsolve5j.cpp


             # Set of source files ${SOURCE}
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/hbio.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/lusol.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/lusol1.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/lusol2.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/lusol6a.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/lusol6l0.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/lusol6u.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/lusol7a.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/lusol8a.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/lusolio.c
             #src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/lusolmain.c
             #src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/mmio.c
             #src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/myblas.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/sparselib.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/bfp_LUSOL.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/lp_LUSOL.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/lp_BFP1.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/lp_BFP2.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/colamd/colamd.c
             #src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/demo/demo.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/fortify.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/ini.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_Hash.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_MDO.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_MPS.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_SOS.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_crash.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_lib.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_matrix.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_mipbb.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_params.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_presolve.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_price.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_pricePSE.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_report.c
             #src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_rlp.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_scale.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_simplex.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_solve/lp_solve.c
             #src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_solveDLL.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_types.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_utils.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_wlp.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/shared/commonlib.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/shared/mmio.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/shared/myblas.c
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/yacc_read.c



             # Set of header files${HEADERS}
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/hbio.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/lusol.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/lusolio.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/lusolmain.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/mmio.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/myblas.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/LUSOL/sparselib.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/bfp_LUSOL.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/bfp_LUSOL/lp_LUSOL.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/bfp/lp_BFP.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/colamd/colamd.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/declare.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/fortify.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/ini.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_Hash.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_MDO.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_MPS.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_SOS.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_bit.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_crash.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_explicit.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_fortify.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_lib.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_matrix.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_mipbb.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_presolve.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_price.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_pricePSE.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_report.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_rlp.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_scale.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_simplex.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_solveDLL.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_types.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_utils.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lp_wlp.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lpkit.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lpsolve.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/lpsolve55/resource.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/shared/commonlib.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/shared/mmio.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/shared/myblas.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/ufortify.h
             src/main/cpp/lp_solve_5.5.2.5_source/lp_solve_5.5/yacc_read.h
             src/main/cpp/lp_solve_5.5.2.5_source/lpsolve_LpSolve.h
             )












# Searches for a specified prebuilt library and stores the path as a
# variable. Because system libraries are included in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.
find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )







# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in the
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       native-lib                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
