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
            
            populateTable();
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
                '<table class="table">'
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
                +        '</tr>'
                +    '{{/data}}'
                +    '</tbody>'
                + '</table>'
            );
            //Заполняем шаблон данными и помещаем на веб-страницу
            $('#table-container').html(template.render(resp));
            
        });
    }
});

