<html>
<head>
    <title>Login</title>
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
        crossorigin="anonymous"
    >
    <link rel="stylesheet" href="/styles/default-styles.css">
    <script src="/scripts/authorizationScript.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-4 offset-md-4">
            <div class="login-form bg-light mt-4 p-4">
                <form action="login" method="post" class="row g-3">
                    <h4>Log in</h4>
                    <div class="col-12">
                        <label for="login">Login</label>
                        <input
                            id="login"
                            type="text"
                            name="login"
                            class="form-control"
                            placeholder="Please enter your login"
                        >
                    </div>
                    <div class="col-12">
                        <label for="password">Password</label>
                        <input
                            id="password"
                            type="password"
                            name="password"
                            class="form-control"
                            placeholder="Please enter your password"
                        >
                    </div>
                    <div class="col-12">
                        <button type="submit" class="btn btn-dark float-end">Login</button>
                    </div>
                </form>
                <hr class="mt-4">
                <div class="col-12">
                    <p class="text-center mb-0">
                        Have not account yet?
                        <a class="authorization-reference" onclick="moveToRegister()">Sign up</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
