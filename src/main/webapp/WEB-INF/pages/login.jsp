<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>ReelGood - Login</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
  <link rel="stylesheet" href="login.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap">
</head>
<body >
 </div>
    <div class="login-page">
      <div class="login-container">
        <div class="login-form-container">    
          <div class="login-form-section active" id="login-section">
            <picture>
              <img src="${pageContext.request.contextPath}/images/ReelGood.png" alt="logo" style="width: 200px;height: 200; "class="center"> 

            </picture>
            <form id="login-form">
              <div class="login-form-group">
                <label for="login-email">Email</label>
                <div class="login-input-group">
                  <i class="fa-solid fa-envelope login-icon"></i>
                  <input type="email" id="login-email">
                  <span class="login-error-message"></span>
                </div>
              </div>
              
              <div class="login-form-group">
                <label for="login-password">Password</label>
                <div class="login-input-group">
                  <i class="fa-solid fa-lock login-icon"></i>
                  <input type="password" id="login-password">
                  <i class="fa-solid fa-eye login-toggle-password"></i>
                  <span class="login-error-message"></span>
                </div>
              </div>
              
                            
              <button type="submit" class="login-submit-btn">
                <span>Login</span>
                <i class="fa-solid fa-arrow-right"></i>
              </button>
            </form>
          </div>
          
          
          
          <div class="login-form-footer">
            <p id="login-footer-text">Don't have an account? <a href="${pageContext.request.contextPath}/register">Sign up</a></p>
          </div>
        </div>
      </div>
    </div>
  </div>


</body>
</html>
