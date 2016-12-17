from . import views
from django.conf.urls import url

urlpatterns = [
    url(r"home/$", views.home),
    url(r"authenticate/$", views.authenticate_user),
    url(r"logout/$", views.logout_user, name="logout"),
    url(r"secret_info", views.get_secret_info, name="secret_info"),
]