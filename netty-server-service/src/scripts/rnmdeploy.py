#!/usr/bin/env python
# -*- coding: utf-8 -*-

from fabric.api import local, cd, sudo
from fabric.api import put, get
from fabric.api import env, task, run


@task
def status():
    run("ps -ef |grep rnm_server-1.0-SNAPSHOT.jar")
    run('screen -wipe', warn_only=True)

@task
def deploy():
    path = '/opt/rnm'
    put('../../target/netty-server-demo-1.0-SNAPSHOT-release.tar.gz',path) #put jmeter to remote:~
    with cd(path):
        run('tar xzf netty-server-demo-1.0-SNAPSHOT-release.tar.gz')
        start()

@task
def uninstalled():
    path = '/opt/rnm'
    with cd(path):
        print 'uninstalled begin'
        stop()
        run('rm -rf netty-server-demo-1.0-SNAPSHOT*')
        print 'uninstalled end'

@task
def stop():
    kill_process(str="netty-server-demo-1.0-SNAPSHOT.jar")

@task
def start():
    run('screen -S netty-server-demo -d -m netty-server-demo-1.0-SNAPSHOT/bin/netty.sh run; sleep 1')
    run('screen -ls |grep netty-server-demo')

@task
def get_hosts():
    #env.user='test'
    #env.password = 'test'
    #env.hosts = ['172.27.233.74', '172.27.233.72', '172.27.244.14', '172.27.233.71', ]
    env.user='root'
    env.password = '123456'
    env.hosts = ['172.27.234.27', ]


@task
def kill_process(str):
    run ("ps ax |grep %s |grep -v grep | awk '{print $1}' | xargs kill -9" % str, warn_only=True)