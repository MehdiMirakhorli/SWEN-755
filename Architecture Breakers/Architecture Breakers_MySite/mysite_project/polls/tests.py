from django.test import TestCase
from django.contrib.auth.models import User
from django.contrib.auth.models import User
from django.contrib.auth.hashers  import make_password


#Command to run the code:
#python manage.py test polls.tests.SessionTests




class SessionTests(TestCase):
	"""
	Tests for authentication
	"""
	def test_valid_authentication(self):
		"""
		Test a successful login (admin/123456)
		"""
		user=User.objects.create_superuser("admin", "admin@gmail.com", "123456")
		user.save()

		form_data = {'username':'admin', 'password':'123456'}
		response = self.client.login(username=user.username,password="123456")
		self.assertEqual(response, True)


