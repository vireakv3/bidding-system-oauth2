<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bid Session</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f8f8;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 80%;
            margin: 0 auto;
            background-color: #ffffff;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .container-flex {
            display: flex;
            justify-content: space-between;
        }
        h1, h2 {
            text-align: center;
            color: #333;
        }
        .card {
            box-sizing: border-box;
            width: 300px;
            background-color: gray;
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
        .box {
            width: 48%;
            padding: 5px;
            background-color: #fafafa;
            border: 1px solid #ddd;
        }
        .box h3 {
            text-align: center;
            color: #333;
        }
        .bidder {
            margin-top: 5px;
            padding: 5px 30px;
            border-top: 1px solid #ddd;
        }
        .bidder p {
            margin: 5px 0;
        }
        .form-container {
            margin-top: 20px;
            padding: 10px;
            background-color: #f2f2f2;
            border: 1px solid #ddd;
        }
        .form-container input[type="number"] {
            padding: 8px;
            width: 250px;
        }
        .form-container button {
            padding: 8px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        .form-container button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<!-- index.ftl -->
<#include "navbar.ftl">
<div class="banner">
    <h1>Welcome, ${session.title}</h1>
    <p style="text-align: center">${session.description}</p>
    <div class="container">
        <div class="card">
            <h3>Item: ${session.item.name}</h3>
            <hr>
            <b>Price: $${session.item.price}</b>
            <#if session.item.description?has_content>
                <p>${session.item.description}</p>
            </#if>
        </div>
        <div class="container-flex">
            <div class="box">
                <h3>Recent Bids</h3>
                <div style="max-height: 400px;overflow-x: hidden;overflow-y: auto;">
                    <#if session.bids?has_content>
                        <#list session.bids as bid>
                            <div class="bidder">
                                <p>Bid Amount: $${bid.amount}</p>
                            </div>
                        </#list>
                    <#else>
                        <p>No bids yet.</p>
                    </#if>
                </div>
            </div>
            <div class="box">
                <h3>Top Bid</h3>
                <#if topBidder?? && topBidder.topBid??>
                    <div class="bidder">
                        <p>Amount: $${topBidder.topBid.amount}</p>
                        <p>Bidder: ${topBidder.user.lastName}</p>
                        <p>Time: ${topBidder.topBid.bidTime}</p>
                    </div>
                <#else>
                    <p>No bids yet.</p>
                </#if>
                <!-- Form to post a new bid -->
                <div class="form-container">
                    <form action="${sessionId}/post-bid" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <label for="bidAmount">Bid Amount:</label>
                        <input type="number" id="bidAmount" name="bidAmount" required>
                        <button type="submit">Post Bid</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>