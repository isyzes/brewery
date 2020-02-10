# Brewery
## Overview
Project for the production and sale of beer

Приложение для производства и реализации пива 
## Entities
Ниже перечисленный сущности в предметной области проекта и их поля.
### Employee(Сотрудник):
Сотрудник работающий на заводе или работающий ранее, но был уволен.
#### Поля:
* id
* name
* department
* wages
* isWorks
* dateStart
* dateEnd
* role
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
* ingredients
#### Связи:     
* Ингредиенты (ingredients)
### Ingredients(ингредиенты):
ингредиенты необходимые для производства пива
#### Поля:
* id
* name
* price
* manufacturer
#### Связи:  
  
### Consumers(Покупатель):
#### Поля:
* id
* name  
#### Связи:   
### Order:
#### Поля:
* id
* price
* products   
#### Связи:  
* Список одиночных заказов(OneOrder)
### OneOrder
#### Поля:
* id
* idBeer
* quantity
#### Связи: 
*      
## User Stories
### JTEP-1 Как Покупатель я хочу купить товар, чтобы обеспечить свой магазин
`Request post /buy/idConsumers`

    [
        {"idBeer" : 1, "quantity" : 5},
        {"idBeer" : 4, "quantity" : 4}          
    ]
`Response: 201 CREATED`

    { 
        "id":14,
        "price":4248,
        "products":[ 
            { 
                "idBeer":1,
                "quantity":5
            },
            { 
                "idBeer":4,
                "quantity":4
            }
        ]
    }
    
    
### JTEP-2 как руководитель я хочу нанимать на работу сотрудников чтобы они выпускали продукцию
`Request post /employee/take`

    {"name" : "Ivanov Ivan Ivanovich", "department" : "Production", "wages" : 2020}
    
`Response: 201 CREATED`

        {"id" : 1}
     
### JTEP-3 как руководитель я хочу увольнять сотрудников чтобы не нарушать законодательство
`Request: PUT /staff/to-dismiss/idEmployee`

`Response: 200 OK`
     
### JTEP-4 как руководитель я хочу получать список всех работников, чтобы выбрать кого уволить
`Request: GET /staff/list`

`Response: 200 OK`

     [
        {"id" : 5, "name" : "Adam Gordon", "department" : "Production", "wages" : 2500, "isWorks" : true, "dateStart" : "15.01.2018", "dateEnd" : null},
        {"id" : 2, "name" : "Carla Williams", "department" : "Production", "wages" : 5070, "isWorks" : true, "dateStart" : "15.01.2018", "dateEnd" : null},
        {"id" : 4, "name" : "Boris Jones", "department" : "Production", "wages" : 1500, "isWorks" : false, "dateStart" : "15.01.2018", "dateEnd" : "14.10.2019"}         
     ]
     
    
### JTEP-5 как руководитель я хочу получать список ассортимента товаров, чтобы выбрать какой изменить/удалить
`Request get /beers/list`

`Response: 200 OK`

     [
        {"id" : 1, "name" : "Garage", "color" : "bright", "fortress" : 12.5, "dateManufacture": "12.12.2020", "shelfLife": 25, "costPrice": 574},   
        {"id" : 2, "name" : "Miller", "color" : "bright", "fortress" : 12.5, "dateManufacture": "12.12.2020", "shelfLife": 25, "costPrice": 755},
        {"id" : 3, "name" : "Heineken", "color" : "dark", "fortress" : 9.5, "dateManufacture": "12.12.2020", "shelfLife": 35, "costPrice": 5756}       
     ]
    
### JTEP-6 как руководитель я хочу изменять информацию о товаре (обновить цену и имя пива), чтобы повысить уровень продаж

`Request PUT /beers/{idBeer}`

    {
        "name" : "Grimbergen",
        "costPrice" : 2551   
    }
    	
`Response: 200 OK`
       
    {"id" : 2, "name" : "Grimbergen" , "color" : "bright", "fortress" : 12.5, "dateManufacture": "12.12.2020", "shelfLife": 25, "costPrice": 2551}
          
### JTEP-7 как руководитель я хочу получать уведомления о заканчивающейся ингредиентах чтобы вовремя закупить нужны ингредиенты
   
### JTEP-8 как руководитель я хочу получать уведомления о заканчивающейся продукции чтобы определять приоритет выпускаемой продукции




