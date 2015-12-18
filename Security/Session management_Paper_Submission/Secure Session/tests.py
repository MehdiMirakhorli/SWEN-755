import unittest
from django.test import TestCase, RequestFactory
from sam2017_app.models import user_model
import django
from django.test import TestCase
from django.test import Client

__author__ = 'Adi'


class TestUserAuthentication(TestCase):

    def setUp(self):
        user = user_model.User.objects.create(first_name='Aditya',
                                              last_name='Landekar',
                                              email='aditya@rit.edu',
                                              password='aditya',)

    def test_login(self):
        response = self.client.post('/', {'email': 'aditya@rit.edu', 'password': 'aditya'})
        self.assertEquals(response.status_code, 302)

    def test_invalid_login(self):

        # wrong username
        response = self.client.post('/', {'email': 'aditya', 'password': 'aditya'})

        # This test should fail and say that 200 != 300
        self.assertEquals(response.status_code, 302)


class TestSessionAuthentication(TestCase):

    def setUp(self):
        user = user_model.User.objects.create(first_name='Aditya',
                                              last_name='Landekar',
                                              email='aditya@rit.edu',
                                              password='aditya',)

    def test_login(self):
        response = self.client.post('/', {'email': 'aditya@rit.edu', 'password': 'aditya'})

        session = self.client.session
        self.assertEquals(session['is_open'], True)
        self.assertEquals(response.status_code, 302)

    def test_session_authentication(self):
        response = self.client.post('/', {'email': 'aditya@rit.edu', 'password': 'aditya'})

        session = self.client.session
        self.assertEquals(session["user_email"], "aditya@rit.edu")
        self.assertEquals(response.status_code, 302)


class TestOpenSession(TestCase):

    def setUp(self):
        user = user_model.User.objects.create(first_name='Aditya',
                                              last_name='Landekar',
                                              email='aditya@rit.edu',
                                              password='aditya',)

        session = self.client.session
        session['user_email'] = "aditya@rit.edu"
        session['is_open'] = True
        session.save()

    def test_session_open(self):
        session = self.client.session
        response = self.client.post('/', {'email': 'aditya@rit.edu', 'password': 'aditya'})
        self.assertEquals(session['is_open'], True)
        self.assertEquals(response.status_code, 302)


class TestRegistration(TestCase):

    def test_registration(self):
        response = self.client.post('/registration/', data={'first_name': 'Aditya',
                                                            'last_name': 'Landekar',
                                                            'email': 'aditya@rit.edu',
                                                            'password': 'aditya',
                                                            'password_verification': 'aditya'})

        self.assertEquals(response.status_code, 302)















