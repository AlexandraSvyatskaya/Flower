<!DOCTYPE html>
<html>
<head>
    <title>Product</title>
</head>
<body>
<h1>EcoRose</h1><hr>
<h4>Подробная инфорация о товаре<h4>
<#list images as img>
<img src="/images/${img.id}" height="60px"/><br><br>
</#list>
<b>Наименование товара: </b>${product.title}<br>
<b>Категория: </b>${product.category}<br>
<b>Описание: </b>${product.description}<br>
<b>Цена: </b>${product.price}<br>
<b>Автор: </b><a href="/user/${product.user.id}">${product.user.name}</a><br>
</form>
</body>
</html>