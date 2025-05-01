<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CINEMANDU - book your tickets</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap">
    <script src="https://unpkg.com/feather-icons"></script>
</head>
<body>
    <div class="container">
        <header>
            <div class="header-content">
                <div style=" width:100%; text-align: center;">
                    <img src="${pageContext.request.contextPath}/images/ReelGood.png" style='height:200px; width:200px;'>
                </div>
                
                    <button class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/login.jsp">Sign In / Register</button>
               
            </div>
        </header>

        <div class="tabs">
            <div class="tabs-list">
                <button class="tab-trigger active" data-tab="now-showing">Now Showing</button>
                <button class="tab-trigger" data-tab="coming-soon">Coming Soon</button>
            </div>

            <div class="tab-content active" id="now-showing">
                <div class="movie-grid">
                    <!-- Now Showing Movies -->
                </div>
            </div>

            <div class="tab-content" id="coming-soon">
                <div class="movie-grid">
                    <!-- Coming Soon Movies -->
                </div>
            </div>
        </div>
    </div>

    <template id="movie-card-template">
        <div class="movie-card">
            <div class="movie-poster">
                <img src="${pageContext.request.contextPath}/images/placeholder.svg" alt="">
                <span class="movie-rating"></span>
            </div>
            <div class="movie-info">
                <h3 class="movie-title"></h3>
                <div class="movie-meta">
                    <span class="movie-duration"></span>
                    <span class="separator">&bull;</span>
                    <span class="movie-genre"></span>
                </div>
                <a href="${pageContext.request.contextPath}/booking" class="btn btn-primary book-btn">Book Tickets</a>
            </div>
        </div>
    </template>

    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>
        // Initialize Feather icons after page load
        document.addEventListener('DOMContentLoaded', function() {
            feather.replace();
        });
    </script>
</body>
</html>