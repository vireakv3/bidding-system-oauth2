<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
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
            /*width: 300px;*/
            background-color: #80808061;
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
            width: 100%;
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
<#--    <h1>Welcome, ${session.title}</h1>-->
    <div class="container">
        <div class="container-flex">
            <div class="box">
                <h3>${title}</h3>
                <div style="max-height: 400px;overflow-x: hidden;overflow-y: auto;">
                    <#if bidsHistoies??>
                        <#list bidsHistoies as bidHis>
                            <div class="card">
                                <h3>Item: ${bidHis.session.item.name}</h3>
                                <hr>
                                <b>Price: $${bidHis.session.item.price}</b>
                                <#if bidHis.session.item.description?has_content><p>Description: $${bidHis.session.item.description}</p></#if>
                                <hr>
                                <b>Session: ${bidHis.session.title}</b>
                                <#if bidHis.session.description?has_content><p>Description: ${bidHis.session.description}</p></#if>
                                <hr>
                                <p>Bid Amount: <b>$${bidHis.bid.amount}</b> </p>
                                <p>Bid Datetime: ${bidHis.bid.bidTime}</p>
                            </div>
                        </#list>
                    <#else>
                        <p>No win bids yet.</p>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>