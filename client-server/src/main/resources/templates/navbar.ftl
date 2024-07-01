<style>
    .navbar {
        background-color: #333;
        overflow: hidden;
        padding: 0;
        margin-bottom: 50px;
    }
    .navbar a {
        float: left;
        display: block;
        color: white;
        text-align: center;
        text-decoration: none;
    }
    .navbar a:hover {
        background-color: #ddd;
        color: black;
    }
    .navbar .user-info {
        float: right;
        color: white;
        padding: 14px 20px;
    }
</style>
<!-- Navigation Bar -->
<div class="navbar">
    <a href="/" style="float: left;color: white;padding: 14px 20px;background-color: darkcyan;">Home</a>
    <a href="/public/all-bids" style="float: left;color: white;padding: 14px 20px;background-color: #555c5e;">Histories</a>
    <a href="/public/win" style="float: left;color: white;padding: 14px 20px;background-color: green;">Win</a>
    <a href="/public/lost" style="float: left;color: white;padding: 14px 20px;background-color: red;">Lost</a>
    <div class="user-info">
        <#if user??>
            Logged in as: ${user.getName()}
        <#else>
            Unknow
        </#if>
    </div>
</div>