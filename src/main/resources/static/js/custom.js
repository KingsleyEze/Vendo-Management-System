jQuery(document).ready(function($) {
   
   "use strict";


   // Tooltip
   jQuery('.tooltips').tooltip({ container: 'body'});

   jQuery('.select-single-hide').select2({
      minimumResultsForSearch: -1
   });


   
   // Popover
   jQuery('.popovers').popover();
   
   // Show panel buttons when hovering panel heading
   jQuery('.panel-heading').hover(function() {
      jQuery(this).find('.panel-btns').fadeIn('fast');
   }, function() {
      jQuery(this).find('.panel-btns').fadeOut('fast');
   });
   
   // Close Panel
   jQuery('.panel .panel-close').click(function() {
      jQuery(this).closest('.panel').fadeOut(200);
      return false;
   });
   
   // Minimize Panel
   jQuery('.panel .panel-minimize').click(function(){
      var t = jQuery(this);
      var p = t.closest('.panel');
      if(!jQuery(this).hasClass('maximize')) {
         p.find('.panel-body, .panel-footer').slideUp(200);
         t.addClass('maximize');
         t.find('i').removeClass('fa-minus').addClass('fa-plus');
         jQuery(this).attr('data-original-title','Maximize Panel').tooltip();
      } else {
         p.find('.panel-body, .panel-footer').slideDown(200);
         t.removeClass('maximize');
         t.find('i').removeClass('fa-plus').addClass('fa-minus');
         jQuery(this).attr('data-original-title','Minimize Panel').tooltip();
      }
      return false;
   });
   
   jQuery('.leftpanel .nav .parent > a').click(function() {
      
      var coll = jQuery(this).parents('.collapsed').length;
      
      if (!coll) {
         jQuery('.leftpanel .nav .parent-focus').each(function() {
            jQuery(this).find('.children').slideUp('fast');
            jQuery(this).removeClass('parent-focus');
         });
         
         var child = jQuery(this).parent().find('.children');
         if(!child.is(':visible')) {
            child.slideDown('fast');
            if(!child.parent().hasClass('active'))
               child.parent().addClass('parent-focus');
         } else {
            child.slideUp('fast');
            child.parent().removeClass('parent-focus');
         }
      }
      return false;
   });
   

   // Menu Toggle
   jQuery('.menu-collapse').click(function() {
      if (!$('body').hasClass('hidden-left')) {
         if ($('.headerwrapper').hasClass('collapsed')) {
            $('.headerwrapper, .mainwrapper').removeClass('collapsed');
         } else {
            $('.headerwrapper, .mainwrapper').addClass('collapsed');
            $('.children').hide(); // hide sub-menu if leave open
         }
      } else {
         if (!$('body').hasClass('show-left')) {
            $('body').addClass('show-left');
         } else {
            $('body').removeClass('show-left');
         }
      }
      return false;
   });

   // Add class nav-hover to mene. Useful for viewing sub-menu
   jQuery('.leftpanel .nav li').hover(function(){
      $(this).addClass('nav-hover');
   }, function(){
      $(this).removeClass('nav-hover');
   });
   
   // For Media Queries
   jQuery(window).resize(function() {
      hideMenu();
   });
   
   hideMenu(); // for loading/refreshing the page
   function hideMenu() {

      if(jQuery('.header-right').css('position') == 'relative') {
          jQuery('body').addClass('hidden-left');
          jQuery('.headerwrapper, .mainwrapper').removeClass('collapsed');
      } else {
          jQuery('body').removeClass('hidden-left');
      }
      
      // Seach form move to left
      if (jQuery(window).width() <= 360) {
         if (jQuery('.leftpanel .form-search').length == 0) {
             jQuery('.form-search').insertAfter($('.profile-left'));
         }
      } else {
         if (jQuery('.header-right .form-search').length == 0) {
             jQuery('.form-search').insertBefore($('.btn-group-notification'));
         }
      }
   }
   
   collapsedMenu(); // for loading/refreshing the page
   function collapsedMenu() {


      if(jQuery('.logo').css('position') == 'relative') {
         jQuery('.headerwrapper, .mainwrapper').addClass('collapsed');
      } else {
         jQuery('.headerwrapper, .mainwrapper').removeClass('collapsed');
      }
   }

});