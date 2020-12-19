FROM nginx
COPY dist /usr/share/nginx/html
COPY entrypoint.sh /
RUN chmod +x /entrypoint.sh
COPY nginx/default.conf /etc/nginx/conf.d/default.conf
ENTRYPOINT [ "/entrypoint.sh" ]
