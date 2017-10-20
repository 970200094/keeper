 !function (win, doc, undefined) {
      var html = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function () {
          var clientWidth = html.clientWidth
          if (!clientWidth) { return }
          html.style.fontSize = 100 * (clientWidth / 360) + 'px'
        }

      if (!doc.addEventListener) return
      win.addEventListener(resizeEvt, recalc, false)
      doc.addEventListener('DOMContentLoaded', recalc, false)
    }(window, document)


