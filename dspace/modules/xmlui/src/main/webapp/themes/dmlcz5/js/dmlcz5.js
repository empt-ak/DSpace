/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$('#my-email').html(function(){
	var e = "help";
	var a = "@";
	var d = "dml";
	var c = ".cz";
	var h = 'mailto:' + e + a + d + c;
	$(this).parent('a').attr('href', h);
	return e + a + d + c;
});