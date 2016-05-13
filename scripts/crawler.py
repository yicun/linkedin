#! /usr/bin/env python
# coding:utf-8

import sys
import urllib2
import urllib
import cookielib

# # 这段代码是用于解决中文报错的问题
reload(sys)
sys.setdefaultencoding("utf8")
#####################################################
#登录人人
loginurl = 'https://www.linkedin.com/uas/login-submit'
logindomain = 'linkedin.com'


class Login(object):
    def __init__(self):
        self.name = ''
        self.pwd = ''
        self.domain = ''

        self.cj = cookielib.LWPCookieJar()
        self.opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(self.cj))
        urllib2.install_opener(self.opener)

    def setLoginInfo(self, username, password, domain):
        """ 设置用户登录信息 """
        self.name = username
        self.pwd = password
        self.domain = domain

    def login(self):
        """ 登录网站 """
        loginparams = {'domain': self.domain, 'session_key': self.name, 'session_password': self.pwd}
        headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36"}
        req = urllib2.Request(loginurl, urllib.urlencode(loginparams), headers=headers)
        response = urllib2.urlopen(req)
        self.operate = self.opener.open(req)
        thePage = response.read()
        print thePage

if __name__ == '__main__':
    userlogin = Login()
    username = 'chyc6278@gmail.com'
    password = 'chyc6278'
    domain = logindomain
    userlogin.setLoginInfo(username, password, domain)
    userlogin.login()