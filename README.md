# Brewery
## Overview
Project for the production and sale of beer
Приложение для производства и реализации пива 
## Entities
Ниже перечисленный сущности в предметной области проекта и их поля.

### User(Пользователь):
Пользователь работающий на заводе или работающий ранее, но был уволен. 
Пользователь котороый хочет купить товар (Покупатель)
#### Поля:
* id
* email
* fio
* birthDate
* selfDescription
* userRole
* department
* wages
* isWorks
* dateStart
* dateEnd
#### Связи:
* Взаимодействуют между собой по ролям

### Beer(Пиво):
выпускаемая продукция
#### Поля:
* id
* name
* color
* fortress
* dateManufacture
* shelfLife
* costPrice
* recipe
* litersInStock
#### Связи:     
* Recipe (recipe)

### Recipe(рецепт):
Руководство по приготовлению пива
#### Поля:
* id
* items
#### Связи:
* список RecipeItem (items)

### RecipeItem(Одна часть рецепта):
Содержит информацию о необходимом ингредиенте и его количестве
#### Поля:
* id
* ingredient
* milligram
#### Связи:
* Ingredient (ingredient)

### Ingredient(ингредиент):
ингредиент необходимый для производства пива
#### Поля:
* id
* name
* milligramsInStock
#### Связи:
 

### OrderEntity(Заказ):
Заказ, который сделал покупатель
#### Поля:
* id
* price
* consumer
* items   
#### Связи:  
* Список OrderItem(items)
* Покупатель UserEntity (consumer)

### OrderItem(Часть заказа)
Содержит информацию о пиве и его количестве в заказе
#### Поля:
* id
* beer
* liters
#### Связи: 
* Beer(beer)

## User Stories

### BWRP-1 Как "Покупатель" я хочу зарегистрироваться в системе чтобы войти в систему
`Request post /sign-up`

    {
        "email" : "vasya@email.com",
        "password" : "qwerty",
        "fio" : "Пупкин Василий Иванович"
    }

`Response: 201 CREATED`

    { 
      "token" : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2YXN5YUBlbWFpbC5jb20iLCJleHAiOjE1ODMwOTM2MDcsImlhdCI6MTU4MzA1NzYwN30.dnJqv88EduiVCBjKKae4yWXp4u-mwPRRSZRCvO5qp5A" 
    }

### BWRP-2 Как "Покупатель" я хочу войти в систему чтобы сделать заказ
`Request post /sign-in`

    {
        "email" : "vasya@email.com",
        "password" : "qwerty"
    }
    
`Response: 200 OK`

    { 
      "token" : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2YXN5YUBlbWFpbC5jb20iLCJleHAiOjE1ODMwOTM2MDcsImlhdCI6MTU4MzA1NzYwN30.dnJqv88EduiVCBjKKae4yWXp4u-mwPRRSZRCvO5qp5A" 
    }

### BWRP-3 Как "Покупатель" я хочу смотреть список товар, чтобы чтобы сделать заказ
`Request get /beers/list`

`Response: 200 OK`

     [
        {"id" : 1, "name" : "Garage", "color" : "bright", "fortress" : 12.5, "dateManufacture": "12.12.2020", "shelfLife": 25, "costPrice": 574},   
        {"id" : 2, "name" : "Miller", "color" : "bright", "fortress" : 12.5, "dateManufacture": "12.12.2020", "shelfLife": 25, "costPrice": 755},
        {"id" : 3, "name" : "Heineken", "color" : "dark", "fortress" : 9.5, "dateManufacture": "12.12.2020", "shelfLife": 35, "costPrice": 5756}       
     ]

### BWRP-4 Как "Покупатель" я хочу купить товар, чтобы обеспечить свой магазин
`Request post /beers/buy`

    {
        "consumer":{"id":4,"fio": "Easy Pub"},
        "items":[
            {"beer":{"id" : 3}, "liters":5},
            {"beer":{"id" : 4}, "liters":4}
        ]
    }
    
`Response: 201 CREATED`

     {
        "price":26.28,
        "consumer":{"id":4,"fio":"Easy Pub"},
        "items":[
            {"beer":{"id":3,"name":"Grimbergen","color":"bright","fortress":12.5,"dateManufacture":"12.12.2020","shelfLife":25,"costPrice":657,"recipe":{"id":3,"items":[{"id":3,"ingredient":{"id":3,"name":"Water","milligramsInStock":647851},"milligram":5}]}},"liters":5},
            {"beer":{"id":4,"name":"Grimbergen","color":"bright","fortress":12.5,"dateManufacture":"12.12.2020","shelfLife":25,"costPrice":657,"recipe":{"id":3,"items":[{"id":3,"ingredient":{"id":3,"name":"Water","milligramsInStock":647851},"milligram":5}]}},"liters":4}
        ]
     }

### BWRP-5 как "Руководитель" я хочу нанимать на работу сотрудников чтобы они выпускали продукцию
`Request post /staff/employee/created`

    {"name" : "Ivanov Ivan Ivanovich", "department" : "Production", "wages" : 2020}
    
`Response: 201 CREATED`

        {"id" : 1}    

### BWRP-6 как "Руководитель" я хочу получать список всех работников, чтобы выбрать кого уволить
`Request: GET /staff/list`

`Response: 200 OK`

     [
        {"id" : 5, "name" : "Adam Gordon", "department" : "Production", "wages" : 2500, "isWorks" : true, "dateStart" : "15.01.2018", "dateEnd" : null},
        {"id" : 2, "name" : "Carla Williams", "department" : "Production", "wages" : 5070, "isWorks" : true, "dateStart" : "15.01.2018", "dateEnd" : null},
        {"id" : 4, "name" : "Boris Jones", "department" : "Production", "wages" : 1500, "isWorks" : false, "dateStart" : "15.01.2018", "dateEnd" : "14.10.2019"}         
     ] 

### BWRP-7 как "Руководитель" я хочу увольнять сотрудников чтобы не нарушать законодательство
`Request: PUT /staff/to-dismiss/{idEmployee}`

`Response: 200 OK`
     

### BWRP-8 как "Руководитель" я хочу получать список ассортимента товаров, чтобы выбрать какой изменить/удалить
`Request get /beers/list`

`Response: 200 OK`

     [
        {"id" : 1, "name" : "Garage", "color" : "bright", "fortress" : 12.5, "dateManufacture": "12.12.2020", "shelfLife": 25, "costPrice": 574},   
        {"id" : 2, "name" : "Miller", "color" : "bright", "fortress" : 12.5, "dateManufacture": "12.12.2020", "shelfLife": 25, "costPrice": 755},
        {"id" : 3, "name" : "Heineken", "color" : "dark", "fortress" : 9.5, "dateManufacture": "12.12.2020", "shelfLife": 35, "costPrice": 5756}       
     ]  

### BWRP-9 как "Руководитель" я хочу изменять информацию о товаре (обновить цену и имя пива), чтобы повысить уровень продаж
`Request PUT /beers/updated/{idBeer}`

    {
        "name" : "Grimbergen",
        "costPrice" : 2551   
    }
    	
`Response: 200 OK`
       
    {"id" : 2, "name" : "Grimbergen" , "color" : "bright", "fortress" : 12.5, "dateManufacture": "12.12.2020", "shelfLife": 25, "costPrice": 2551}

### BWRP-10 как "Руководитель" я хочу получать уведомления о заканчивающейся продукции чтобы определять приоритет выпускаемой продукции
`Request post /beers/buy`
    
    {
        "consumer":{"id":4,"fio": "Easy Pub"},
        "items":[
            {"beer":{"id" : 3}, "liters":5435235},
            {"beer":{"id" : 4}, "liters":9234344}
        ]
    }
`Response: 400 BAD_REQUEST`

### BWRP-11 как "Руководитель" я хочу добавлять новые виды пива для производства чтобы расширить ассортимент
`Request post /beers/add`

    {"id":1,
        "name":"Garage",
        "color":"bright",
        "fortress":12.5,
        "shelfLife":25,
        "costPrice":574,
        "recipe":[{"ingredient_id" : 2, "milligram" 158}, {"ingredient_id" : 4, "milligram" 1528}]
    }
`Response: 200 OK`

    {"id" : 2}

### BWRP-12 как "Руководитель" я хочу получать список всех заказов полученных в текущем месяце для определения часто покупаемого товара 
`Request get /orders/list`

`Response: 200 OK`

    [
        {"id" : 1, "price" : "124.24", "user_id" : 2, "item" : [{"id" : 1, "beer_id" : "12", "liters" : 234, "order_id" : 1}]},   
        {"id" : 2, "price" : "5585.51", "user_id" : 4, "item" : [{"id" : 5, "beer_id" : "22", "liters" : 24, "order_id" : 2}]},
        {"id" : 3, "price" : "5483.15", "user_id" : 8, "item" : [{"id" : 4, "beer_id" : "124", "liters" : 2324, "order_id" : 3}]}       
    ]

### BWRP-13 как "Руководитель" я хочу обновлять количесто готового пива на складе 
`Request get /orders/updated`
    
        {"idBeer" : 3, "liters" : 2551}
        
`Response: 200 OK`
    
    {"idBeer" : 3, "nameBeer" : "Grimbergen", "totalLiters" : 8776}}

### BWRP-14 как "Руководитель" я хочу получать уведомления о заканчивающейся ингредиентах чтобы вовремя закупить нужны ингредиенты
`Request get /orders/updated`

    {"idBeer" : 3, "liters" : 2147483647}

`Response: 200 BAD_REQUEST`

### BWRP-15 как "Руководитель" я хочу заказывать ингредиенты если они закончились
`Request put /ingredients//buy`

    {"idIngredient" : "3", "milligramsInStoc6k" : 2551}

`Response: 200 OK` 

    {"idIngredient":3,"totalMilligramsInStock":647851}