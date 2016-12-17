from . import forms
from time import time
from SSM_project import settings
from django.shortcuts import render, redirect
from django.contrib.auth.decorators import login_required
from django.contrib.auth import authenticate, login, logout

INDEX_PAGE = "SSM_app/index.html"
HOME_PAGE = "SSM_app/home.html"

HOME_PAGE_URL = "/SSM_app/home/"
AUTHENTICATE_PAGE_URL = "/SSM_app/authenticate/"

USER_GROUPS = [
    "admin_group",  # super powers
    "manager_group",  # cannot request message
    "regular_user_group",
]


# Create your views here.
def authenticate_user(request):
    login_form = forms.LoginForm(request.POST or None)

    context = {"form": login_form, }

    if login_form.is_valid():
        username = login_form.cleaned_data["username"]

        user = authenticate(username=username, password=login_form.cleaned_data["password"])

        if user is None:
            # Quick and dirty solution...
            context["error"] = "INVALID USER..."

            return render(request, INDEX_PAGE, context)

        else:
            if len(user.groups.all()) > 1:
                context["error"] = "TOO MANY RULES FOR ONE USER... A USER MUST BELONG TO ONE GROUP."

            elif len(user.groups.all()) == 0:
                context["error"] = "USER MUST BELONG TO A GROUP. NO RULE APPLIED."

            else:
                group = user.groups.all()[0].name

                if group == USER_GROUPS[0] or group == USER_GROUPS[1]:
                    # Open session
                    login(request, user)

                    request.session["init_time_session"] = time()  # start time in seconds...

                    request.session["user_group"] = group
                    request.session["username"] = username  # Identifier...

                    return redirect(HOME_PAGE_URL)

                else:
                    context["error"] = "NOT AUTHORIZED USER TO LOGIN..."

    # Default return page...
    return render(request, INDEX_PAGE, context)


@login_required(login_url=AUTHENTICATE_PAGE_URL)
def home(request):
    if verify_session_timeout(request.session["init_time_session"]):
        return logout_user(request)

    request.session["init_time_session"] = time()

    return render(request, HOME_PAGE, {"username": request.session["username"], "user_group": request.session["user_group"]})


@login_required(login_url=AUTHENTICATE_PAGE_URL)
def logout_user(request):
    logout(request)

    return redirect(AUTHENTICATE_PAGE_URL)


@login_required(login_url=AUTHENTICATE_PAGE_URL)
def get_secret_info(request):
    print("GET_SECRET_INFO")
    if verify_session_timeout(request.session["init_time_session"]):
        return logout_user(request)

    request.session["init_time_session"] = time()

    context = {
        "username": request.session["username"],
        "user_group": request.session["user_group"]
    }

    if context["user_group"] != USER_GROUPS[1]:
        context["info"] = "YOU ARE FIRED."

    else:
        context["info"] = "YOU ARE NOT AUTHORIZED TO GET THIS INFO."

    return render(request, HOME_PAGE, context)


def verify_session_timeout(init_time_session):
    if (time() - init_time_session) >= settings.SESSION_TIMEOUT:
        print("TIME OUT!!!!")
        return True

    return False