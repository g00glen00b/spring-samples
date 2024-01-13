document.body.addEventListener('htmx:configRequest', (evt) => {
  evt.detail.headers['accept'] = 'text/html-partial';
  if (evt.detail.verb !== 'get') {
    const csrfHeader = document.querySelector('meta[name=csrf-header]').getAttribute('content');
    const csrfToken = document.querySelector('meta[name=csrf-token]').getAttribute('content');
    if (csrfHeader != null && csrfToken != null) {
      evt.detail.headers[csrfHeader] = csrfToken;
    }
  }
});