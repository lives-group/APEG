# $Id: make.py,v 1.30 2003/01/03 00:21:45 gelderen Exp $
#
# Cryptix JCE Make Script
#
# Copyright (C) 1995-2001 The Cryptix Foundation Limited.
# All rights reserved.
#
# Use, modification, copying and distribution of this software is subject
# the terms and conditions of the Cryptix General Licence. You should have
# received a copy of the Cryptix General Licence along with this library;
# if not, you can download a copy from http://www.cryptix.org/ .
#
# Quick hack by Jeroen C. van Gelderen (gelderen@cryptix.org) who got tired
# from trying to create cross platform (*@&#($ shell scripts/batch files.


import os
import os.path
import shutil
import stat
import sys
import time

#
# Need to set these in environment or the make will crash
#
JAVAC       = os.environ['JAVAC']
JAVAC_FLAGS = os.environ['JAVAC_FLAGS']
CLASSPATH   = os.environ['CLASSPATH']
CLASSPATH11 = os.environ['CLASSPATH11']

if CLASSPATH == CLASSPATH11: 
    JDK11=1
else: 
    JDK11=0


def target_clean():
    if os.path.exists('build'):
        print '  Removing build directory'
        shutil.rmtree('build')


def target_compat():
    generic_target(
        ('java.security', 
         'java.security.spec', 
         'java.security.interfaces',
         'java.security.cert',
         'java.util'),
        'compat',
        ('',),
        CLASSPATH,
        CLASSPATH11)


def target_api():
    generic_target(
        ('cryptix.jce.util',
         'javax.crypto', 
         'javax.crypto.spec', 
         'javax.crypto.interfaces'),
        'api',
        ('',),
        CLASSPATH,
        CLASSPATH)


def target_provider_1():
    generic_target(
        ('cryptix.jce',
         'cryptix.jce.provider.asn',
         'cryptix.jce.provider.util'),
        'provider',
        ('../api',),
        CLASSPATH,
        CLASSPATH)

def target_provider_2():
    generic_target(
        ('cryptix.jce.provider', 
         'cryptix.jce.examples', 
         'cryptix.jce.provider.key', 
         'cryptix.jce.provider.keyfactory', 
         'cryptix.jce.provider.cipher', 
         'cryptix.jce.provider.dh', 
         'cryptix.jce.provider.dsa', 
         'cryptix.jce.provider.mac',
         'cryptix.jce.provider.md',  
         'cryptix.jce.provider.random',
         'cryptix.jce.provider.rsa',
         'cryptix.jce.provider.parameters', 
         'cryptix.jce.provider.elgamal'),
        'provider',
        ('../api',),
        CLASSPATH,
        CLASSPATH)


def target_tests():
    generic_target(
        ('cryptix.jce.test',),
        'tests',
        ('../api', '../provider'),
        CLASSPATH,
        CLASSPATH)


def target_all():
    target_compat()
    target_api()
    target_provider_1()
    target_provider_2()
    target_tests()


def target_jars():
    target_all()
    generic_makejar('compat', 'cryptix-jce-compat.jar')
    generic_makejar('api', 'cryptix-jce-api.jar')
    generic_makejar('provider', 'cryptix-jce-provider.jar')
    generic_makejar('tests', 'cryptix-jce-tests.jar')


def target_dist():
    target_clean()
    target_jars()
    os.system('jarsigner -keystore /.cryptix/cryptix.keystore build/jars/cryptix-jce-provider.jar cryptix')

    print '  Copying files'
    makedirs('build/dist/')

    # copy jars
    shutil.copytree('build/jars', 'build/dist/bin')

    # copy source and remove CVS dirs
    shutil.copytree('src', 'build/dist/src')
    remove_recursive('build/dist/src', 'CVS')

    # copy doco
    shutil.copy('doc/README.TXT', 'build/dist/README.TXT')
    shutil.copy('LICENCE.TXT',    'build/dist')
    shutil.copy('make.py',        'build/dist')

    date = time.strftime("%Y%m%d", time.gmtime(time.time()))
    fname = "cryptix-jce-" + date + "-snap.zip"
    print "  Creating " + fname
    os.system("cd build/dist && zip -9rq ../" + fname + " .")


#
# Recursively remove all directories named 'name',
# starting in directory 'dir'
#
def remove_recursive(dir, name):
    for f in os.listdir(dir):
        path = '%s/%s' % (dir, f)
        mode = os.stat(path)[stat.ST_MODE]
        if stat.S_ISDIR(mode):
            if f == name:
                shutil.rmtree(path)
            else:
                remove_recursive(path, name)


def generic_makejar(dir, name):
    makedirs('build/jars/')
    os.chdir('build/classes/' + dir)
    print '  Creating ' + name
    os.system('jar -cf ../../jars/' + name + ' *')
    os.chdir('../../..')


def generic_target(classes, output_dir, extra_classpath, sys_classpath, boot_classpath):
    makedirs('build/classes/' + output_dir)
    os.chdir('build/classes/' + output_dir)

    my_classpath = os.pathsep
    for a in extra_classpath:
        my_classpath = my_classpath + a + os.pathsep

    if output_dir != 'compat':
        if JDK11==1:
            my_classpath = my_classpath + '../compat' + os.pathsep

    if JAVAC=='jikes':
        my_classpath = my_classpath + boot_classpath
        command = (JAVAC + ' ' +
                   JAVAC_FLAGS + ' ' +
                   '-d . ' +
                   '-classpath .' + my_classpath )
    else:
        my_classpath = my_classpath + sys_classpath
        command = (JAVAC + ' ' +
                   JAVAC_FLAGS + ' ' +
                   '-d . ' +
                   '-classpath .' + my_classpath + ' ' +
                   '-bootclasspath ' + boot_classpath)

    files = ''
    print '  Building:'
    for c in classes:
        print '    ' + c
        files = files + '' + os.path.normcase(' ../../../src/' + c + '/*.java')
    os.system(command + files)

    os.chdir("../../..")

def makedirs(path):
    try:    os.makedirs(path)
    except: pass


if __name__ == "__main__":
    print 'Cryptix JCE Make Script - http://www.cryptix.org/\n'

    if JDK11==0:
        print "Assuming JDK 1.2.x mode\n"
    else:
        print "Assuming JDK 1.1.x mode\n"

    if len(sys.argv) == 1:
        sys.argv.append("all")

    for target in sys.argv[1:]:
        try:
            print 'Making', target
            eval( 'target_' + target + '()' )
        except NameError, e:
            print "Undefined target:", e
