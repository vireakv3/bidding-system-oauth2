<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome Banner and Sessions</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .banner {
            background-color: #4CAF50;
            color: white;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .banner h1 {
            margin: 0;
            font-size: 2.5em;
        }
        .banner p {
            margin: 10px 0 0;
            font-size: 1.2em;
        }
        .card-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            margin: 50px;
        }
        .card {
            box-sizing: border-box;
            width: 300px;
            background-color: #fff;
            padding: 15px;
            border-radius: 5px;
            margin: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .card h3 {
            margin: 0;
            font-size: 1.1em;
        }
        .card p {
            margin: 5px 0;
            font-size: 0.9em;
        }
        .link-button {
            display: inline-block;
            padding: 5px 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<!-- index.ftl -->
<#include "navbar.ftl">
<div class="banner">
    <h1>Welcome, ${user.getName()}!</h1>
    <p>We're glad to have you here.</p>
</div>
<h2 style="text-align: center">Avaliable Session Here</h2>
<hr>
<div class="card-container">
    <#list sessions as session>
        <div class="card">
            <h3>${session.title}</h3>
            <hr>
            <#assign item = session.item>
            <#if item??>
                <b>Item: ${item.name}</b>
                <b>Price: $${item.price}</b>
                <#if item.description??><p>Description:${item.description}</p></#if>
                <hr>
            </#if>
            <p>Start: ${session.startTime}</p>
            <p>Duration: ${session.durationMinute} Minutes</p>
            <p>Status: <span style="background-color:#4CAF50">${session.status}</span></p>
            <hr>
            <p>Description: ${session.description}</p>
            <hr>
            <a href="/public/join-session/${session.id}" class="link-button">Join Bid Session</a>
        </div>
    </#list>
</div>
</body>
</html>
