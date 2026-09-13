/* =============================================================================
 * modal.js  —  모달(대화상자) 컴포넌트
 * -----------------------------------------------------------------------------
 * component.js에서 분리된 모달 전용 스크립트. component.js보다 먼저 로드되어야 함
 *   <script th:src="@{/js/modal.js}" defer></script>
 *   <script th:src="@{/js/component.js}" defer></script>   <- 순서 중요 (modal.js가 먼저)
 *
 * 자동 초기화 : data-modal / data-modal-open="id" 속성이 붙은 요소를 찾아 켭니다.
 * 수동 제어   : UI.modal.open(id) / UI.modal.close(id)
 * ========================================================================== */
(function (global) {
  "use strict";

  const $  = (sel, ctx = document) => ctx.querySelector(sel);
  const $$ = (sel, ctx = document) => Array.from(ctx.querySelectorAll(sel));
  const onceInit = (el, key) => {
    if (el.dataset[key] === "ready") return false;
    el.dataset[key] = "ready";
    return true;
  };
  const prefersReducedMotion = () =>
    global.matchMedia && global.matchMedia("(prefers-reduced-motion: reduce)").matches;

  const FOCUSABLE = 'a[href],button:not([disabled]),input:not([disabled]),select:not([disabled]),textarea:not([disabled]),[tabindex]:not([tabindex="-1"])';
  let lastFocused = null;

  function open(id) {
    const modal = document.getElementById(id);
    if (!modal || !modal.hasAttribute("data-modal")) return;
    lastFocused = document.activeElement;
    modal.hidden = false;
    // 다음 프레임에 클래스 추가 → CSS 트랜지션 발동
    requestAnimationFrame(() => modal.classList.add("is-open"));
    document.body.classList.add("modal-open");

    const panel = $(".modal__panel", modal);
    const focusables = panel ? $$(FOCUSABLE, panel) : [];
    (focusables[0] || panel || modal).focus({ preventScroll: true });

    modal._onKeydown = (e) => {
      if (e.key === "Escape") close(id);
      if (e.key === "Tab" && focusables.length) {
        const first = focusables[0];
        const last = focusables[focusables.length - 1];
        if (e.shiftKey && document.activeElement === first) { e.preventDefault(); last.focus(); }
        else if (!e.shiftKey && document.activeElement === last) { e.preventDefault(); first.focus(); }
      }
    };
    document.addEventListener("keydown", modal._onKeydown);
  }

  function close(id) {
    const modal = document.getElementById(id);
    if (!modal) return;
    modal.classList.remove("is-open");
    document.body.classList.remove("modal-open");
    if (modal._onKeydown) document.removeEventListener("keydown", modal._onKeydown);

    const done = () => { modal.hidden = true; modal.removeEventListener("transitionend", done); };
    if (prefersReducedMotion()) done();
    else {
      modal.addEventListener("transitionend", done);
      setTimeout(done, 400); // 안전장치
    }
    if (lastFocused) lastFocused.focus({ preventScroll: true });
  }

  // root 안의 [data-modal-open]/[data-modal] 요소를 찾아 이벤트를 연결
  function init(root = document) {
    $$("[data-modal-open]", root).forEach((btn) => {
      if (!onceInit(btn, "modalTrigger")) return;
      btn.addEventListener("click", () => open(btn.getAttribute("data-modal-open")));
    });
    $$("[data-modal]", root).forEach((modal) => {
      if (!onceInit(modal, "modalReady")) return;
      $$("[data-modal-close]", modal).forEach((el) =>
        el.addEventListener("click", () => close(modal.id))
      );
    });
  }

  // component.js가 이미 만들어둔 UI 객체가 있으면 거기에 얹고, 없으면 새로 만듦
  global.UI = global.UI || {};
  global.UI.modal = { open, close, init };
})(window);
