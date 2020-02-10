## Brewery

# Entities

    Employee:
        id
        name
        department
        wages
        isWorks
        dateStart
        dateEnd

    Beer:
        id
        name
        color
        fortress
        dateManufacture
        shelfLife(дней)
        costPrice
        ingredients(какой и сколько)
      
    Ingredients:
        id
        name
        price
        manufacturer
    
    склад готовой продукции
        capacity
        inReserve
        
    склад ингредиентов
        capacity
        inReserve
        
    Consumers:
        id
        name
        
    Orders:
        id
        price
        products
 
# User Stories
    GPB-1 Как потребитель я хочу купить товар
    Request post /buy/idПотребителя
        `{
            {"idBeer" : 1, "quantity" : 5}
            {"idBeer" : 4, "quantity" : 4}
            ...
        }`
    Response: {"id" : 14}
    
    как руководитель я хочу:
    GPB-2 нанимать на работу сотрудников
     Request post /employee/take
        `{"name" : сотрудник, "department" : Производство, "wages" : 2020}`
     Response 
        {"id" : 1}
     
GPB-3 увольнять сотрудников
     Request get /сотрудники/уволить/idCотрудника
     Response OK
     
GPB-3 получать список всех работников
     Request get /сотрудники/list
     Response 
     `{
        {"id" : 1, "имя" : сотрудник1, "отдел" 5, "зп" : 500}
        {"id" : 2, "имя" : сотрудник2, "отдел" 5, "зп" : 500}
        {"id" : 4, "имя" : сотрудник3, "отдел" 5, "зп" : 500}         
        ...
     }`
     
GPB-4 получать инфрмацию о каждом работнике отдельно
     Request get /сотрудники/list/id
     Response {"id" : 1, "имя" : сотрудник1, "отдел" 5, "зп" : 500, "дата устройства на работу" 12.12.12}
     
GPB-5 получать список ассортимента
     Request get /beers/list
     Response    
     `{
        {"id" : 1, "название1" : Пиво1, "срок годности" 5}      
        {"id" : 2, "название2" : Пиво2, "срок годности" 5}   
        {"id" : 3, "название3" : Пиво3, "срок годности" 5}       
        ...
     }`
    
GPB-5 изменять информацию о товаре (обновть цену и имя пива)
    Request
    	Запрос: POST /товары/idbeer
    	{
    	    новая цена цена : 2.1
    	    новое имя : пиво
    	}
    Response
       {id : 1, имя : пиво, нена : 2.1}

GPB-6 получать информация о каждом товаре
    Request
        	Запрос: get /товары/list/id
    Response 
            {"id" : 1, "имя" : пиво, "цена" 5.1, "цвет": светлое, "крепость": 12, "годности срок ": 12}
             
GPB-7 получать уведомления о заканчивающейся продукции/материалах
   
GPB-8 информация о наличие денежных средств
    Request Запрос: get /brewery/getMoney
    Response {"Money" : 15755.14}







