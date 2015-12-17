(function() {
    var app;
    var view = {};
    /**
     * [Login description]
     */
    view.Login = function(){
        /**
         * [error description]
         * @type {[type]}
         */
        this.error = ko.observable();
        /**
         * [user description]
         * @type {[type]}
         */
        this.user = ko.observable();
        /**
         * [password description]
         * @type {[type]}
         */
        this.password = ko.observable();
        /**
         * [submit description]
         * @return {[type]} [description]
         */
        this.submit = function() {
            var self = this;
            $.ajax('/auth', {
                data: {
                    name: self.user(),
                    password: self.password()
                },
                statusCode: {
                    401: function() {
                        self.error("Invalid user/password!");
                    }
                }
            }).then(function(response) {
                app.setSession({
                    token: response.token,
                    user: self.user()
                });
                page('/');
            }).fail(function() {
                self.error("Server error.");
            });
        };
    };

    /**
     * [UserList description]
     */
    view.UserList = function(){
        /**
         * [users description]
         * @type {[type]}
         */
        this.users = ko.observable([]);
        /**
         * [error description]
         * @type {[type]}
         */
        this.error = ko.observable();
        var self = this;
        $.ajax('/users', {
            statusCode: {
                403: function() {
                    self.error("Error: User not authorized to see user list.");
                },
                419: function() {
                    app.session().expired = true;
                    page.redirect('/login');
                }
            }
        }).then(function(response) {
            self.users(response.users);
        }).fail(function() {
            self.error("Server error.");
        });
    }

    /**
     * [Session description]
     */
    function Session(){
        this.token = null;
        this.expired = false;
        this.authenticated = function() {
            return !!this.token;
        };
    }

    /**
     * [App description]
     */
    function App() {
        /**
         * [view description]
         * @type {[type]}
         */
        this.view = ko.observable({});
        /**
         * [session description]
         * @type {[type]}
         */
        this.session = ko.observable(new Session());
        /**
         * [loadSession description]
         * @return {[type]} [description]
         */
        this.loadSession = function() {
            this.setSession(JSON.parse(cookie.get('session', '{}')));
        };
        /**
         * [setSession description]
         * @param {[type]} session [description]
         */
        this.setSession = function(session) {
            if(!session.token) {
                return;
            }
            cookie.set('session', JSON.stringify(session));
            this.session($.extend(new Session(), session));
            $.ajaxSetup({
                data: {
                    token: session.token
                }
            });
        };
        /**
         * [clearSession description]
         * @return {[type]} [description]
         */
        this.clearSession = function() {
            cookie.remove('session');
            delete $.ajaxSettings.data["token"];
            this.session(new Session());
        };

        this.loadSession();
    }

    app = new App();

    function requireAuth(ctx, next){
        if (!app.session().authenticated()) {
            return page.redirect('/login');
        }
        next();
    }

    page('/', function() {
        page.redirect('/home');
    });
    page('/login', function() {
        app.view({
            name: 'template-login',
            data: new view.Login()
        });
    });
    page('/logout', function() {
        app.clearSession();
        page.redirect('/login');
    });
    page('/userlist', requireAuth, function(ctx) {
        app.view({
            name: 'template-userlist',
            data: new view.UserList()
        });
    });
    page('/home', requireAuth, function(ctx) {
        app.view({
            name: 'template-home'
        });
    });
    page('*', function() {
        app.view({
            name: 'template-notfound'
        });
    });
    page({
        hashbang: true
    });
    ko.applyBindings(app);
})();
