$(document).ready(function (){
    
    populateTable();
    
    //Final version
    $('form#add-news').submit(function (ev){
        
        //Предотвращение отправки формы
        ev.preventDefault();
        
        //console.log(ev);
        
        // Находим форму отправки заказа на странице
        var $form = $(this);

        //Получаем значения из полей ввода формы
        var $inputs = $form.find("input, textarea");

        //Отключаем поля ввода формы на время отправки запроса
        $inputs.prop("disabled", true);
        
        $.ajax({
            url: "/news",
            data: {
                'action' : 'create'
                , 'title' : $('#title').val()
                , 'content' : $('#content').val()
            },
            type: "POST",
            cache : false
        }).done(function(resp) {
            
        	if(resp.data[0] == 'created'){
        		
        		alert('News created');

                populateTable();
        	} else {
        		
        		alert('Error: ' + resp.error);
        	}
        });
        $inputs.prop("disabled", false);
    });
    
    function populateTable(){

        $('#table-container').html("<div class='progress'><div class='indeterminate'></div></div>");
        
        $.ajax({
            url: "/news",
            data: {
                'action' : 'fetch-all-news'
            },
            type: "POST",
            cache : false
        }).done(function(resp) {
            
            var template = Hogan.compile(
        		'<div class="row">'
        		+ '<button class="btn waves-effect waves-light" type="button" id="deleteNews" name="deleteNews">Delete<i class="material-icons right">delete_forever</i></button>'
        		+ '</div>'
        		+ '<div class="row">'
	                + '<table class="table">'
	                +  '<thead>'
	                +    '<tr>'
	                +      '<th>ID</th>'
	                +       '<th>заголовок</th>'
	                +       '<th>контент</th>'
	                +    '</tr>'
	                +  '</thead>'
	                +  '<tbody>'
	                +  '{{#data}}'
	                + 	'<tr>'
	                +           '<th scope="row">{{id}}</th>'
	                +           '<td>{{title}}</td>'
	                +           '<td>{{content}}</td>'
	                +   '</tr>'
	                +    '{{/data}}'
	                +    '</tbody>'
	                + '</table>'
                + '</div>'
            );
            //Заполняем шаблон данными и помещаем на веб-страницу
            $('#table-container').html(template.render(resp));
            
            $('#deleteNews').unbind("click");
            //Обработчик кнопки Удалить
            $('#deleteNews').click(function(ev){

                ev.preventDefault();
                var newsId = $('.selectedTableRow').find('th').text();
                $.ajax({
                    url: "/news",
                    dataType: 'json',
                    type: "POST",
                    data: { 
                        'action': 'delete-news'
                        , 'news-id' : newsId
                    },
                    cache : false
                }).done(function(resp) {

                    //Проверяем, успешно ли выполнено удаление записи о заказе
                    if (resp.data[0] == 'deleted') {
                        populateTable();
                    } //Иначе сообщаем об ошибке (далее можно заменить на отображение сообщения в форме)
                    else {
                        alert('Ошибка удаления новости');
                    }
                });
            });
            
            //Блокируем кнопки работы со строками таблицы, пока не будет выбрана строка
            $("#deleteNews").attr('disabled', '');
            //Устанавливаем обработчик кликов на все строки таблицы кроме заголовка
            $("table tr:not(:first)").unbind("click");
            $("table tr:not(:first)").click(function(){

                //Разблокируем кнопки, когда выбрана строка таблицы
                $("#deleteNews").removeAttr('disabled');
                //Отмечаем текст выбранной строки зеленым цветом, с остальных строк выделение убираем
                //(оно могло быть ранее установлено на одну из строк)
                $(this).addClass("selectedTableRow").siblings().removeClass("selectedTableRow");
            });
        });
    }
});

