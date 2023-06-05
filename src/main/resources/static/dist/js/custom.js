var lang_kor = {
	"decimal": "",
	"emptyTable": "데이터가없습니다."
	, "info": "_START_-_END_(총_TOTAL_행)",
	"infoEmpty": "0건",
	"infoFiltered": "(전체_MAX_명중검색결과)",
	"infoPostFix": "",
	"thousands": ",",
	"lengthMenu": "_MENU_개씩보기",
	"loadingRecords": "로딩중...",
	"processing": "처리중...",
	"search": "검색:",
	"zeroRecords": "검색된데이터가없습니다.",
	"paginate": {
		"first": "첫페이지",
		"last": "마지막페이지",
		"next": "다음",
		"previous": "이전"
	},
	"aria": {
		"sortAscending": ":오름차순정렬",
		"sortDescending": ":내림차순정렬"
	}
};

function convertFormToJSON(form) {
	return $(form)
		.serializeArray()
		.reduce(function (json, { name, value }) {
			json[name] = value;
			return json;
		}, {});
}

function objectifyForm(formArray) {
	var returnArray = {};
	for (var i = 0; i < formArray.length; i++) {
		returnArray[formArray[i]['name']] = formArray[i]['value'];
	}
	return returnArray;
}

function pw_status(num) {
	if ($('#password' + num).attr('type') == 'password') {
		$('#password' + num).attr('type', 'text');
		$('#eye' + num).addClass('fa-eye').removeClass('fa-eye-slash');
	} else {
		$('#password' + num).attr('type', 'password');
		$('#eye' + num).addClass('fa-eye-slash').removeClass('fa-eye');
	}
}

$(document).on('click', '#btn-modify', function () {
	$('#modal-modify').modal('show');
}).on('input keyup change keyup', '#password1,#password2,#password3', function () {
	$('#btn-submit-modify').attr('disabled', !validateModify());
}).on('submit', '#formModify', function (e) {
	e.preventDefault();
	// var data = JSON.stringify($('#formModify').serialize());
	var data = JSON.stringify({ passwordOriginal: $('#password1').val(), passwordNew: $('#password2').val() });
	$.ajax({
		url: "/api/admin/modify.do", type: 'POST', data: data,
		contentType: "application/json;charset=UTF-8",
		complete: function (e, xhr, settings) {
			if (e.status == 200) {
				$('#formModify')[0].reset();
				$('#modal-modify').modal('hide');
			} else {
				alert('현재 비밀번호가 잘못되었습니다.');
			}
		}
	});
});

function validateModify() {
	return $('#password1').val().length > 0 && $('#password2').val().length > 0 && $('#password2').val() == $('#password3').val();
}