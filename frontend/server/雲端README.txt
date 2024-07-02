匯出:

$ sudo docker ps -a
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS                    PORTS               NAMES
7691a814370e        ubuntu:14.04        "/bin/bash"         36 hours ago        Exited (0) 21 hours ago                       test
$ sudo docker export 7691a814370e > ubuntu.tar

dockerignore 文件設定:
https://www.linuxea.com/2297.html
PS:dockerignore 要放跟程式同ROOT，不然不會過濾( COPY * /{WORKDIR} )

#nodejs 使用docker
https://nodejs.org/zh-cn/docs/guides/nodejs-docker-webapp

# docker build
docker build . -t sa2g
docker build --build-arg VAR1=dev .
docker build --build-arg node_env=dev . -t  asia-east1-docker.pkg.dev/hitrustpay-d-pgw-ext/hitrustpay-sa/system-admin-front-end
docker build --build-arg node_env=test . -t asia-east1-docker.pkg.dev/hitrustpay-t-pgw-ext/hitrustpay-sa/system-admin-front-end
docker build --build-arg node_env=prod . -t asia-east1-docker.pkg.dev/hitrustpay-pgw-ext/hitrustpay-sa/system-admin-front-end

# docker 綁定 images
docker tag sc2g asia-east1-docker.pkg.dev/hitrustpay-d-pgw/hitrustpay-service-center-frontend/hitrustpay-service-center-frontend]

# docker run 
docker run sc2g -p 8080:8081 //把8080 port 轉成 8081

#推 images 到 Artifact registry
docker push asia-east1-docker.pkg.dev/hitrustpay-d-pgw-ext/hitrustpay-sa/system-admin-front-end
docker push asia-east1-docker.pkg.dev/hitrustpay-t-pgw-ext/hitrustpay-sa/system-admin-front-end
docker push asia-east1-docker.pkg.dev/hitrustpay-pgw-ext/hitrustpay-sa/system-admin-front-end

#推 images 到 Artifact registry

#denied: Permission "artifactregistry.repositories.uploadArtifacts" denied on resource "projects/PROJECT_ID/locations/asia-south1/repositories/images" (or it may not exist)
解法:
1.登入 gcloud: gcloud auth login
2.設定 Configure docker: gcloud auth configure-docker 	asia-east1-docker.pkg.dev (make sure to specify appropriate region)
https://stackoverflow.com/questions/75840164/permission-artifactregistry-repositories-uploadartifacts-denied-on-resource-usin

#linux 資料夾分大小寫!!!!

## log檔
雲端LOG需用到 @google-cloud/logging 套件
區分地端或雲端
地端走log4j,雲端走@google-cloud/logging
const {Logging} = require('@google-cloud/logging');
const logging = new Logging(env.CLOUD_PROJECT_ID); 
const log = logging.log(env.CLOUD_LOGGING_NAME);
const metadata = {
	severity: 'INFO',
}
log.write(log.entry(metadata, ...args));

##docker 錯誤
1.Docker Desktop requires a newer WSL kernel version
更新WSL
wsl --update

vmmem記憶體過高
1.關掉Docker Desktop
2.關掉WSL :wsl --shutdown

##GCP LOG(查被http_load_balancer擋下來條件)
resource.type:(http_load_balancer) AND jsonPayload.enforcedSecurityPolicy.name:(backend-default-policy)
AND jsonPayload.enforcedSecurityPolicy.outcome:(DENY)

## GITLAB
可下載套件:
https://www.jasperstudy.com/2018/07/sublime-text-3-git-github.html

Git global setup
git config --global user.name "Shihcheng"
git config --global user.email "shihchenghuang@hitrust.com.tw"

手動:
新增
git add 資料夾/檔案 
git add .全部

commit 
git commit -m "1.XXXX" -m "2.xxxxx"

build merge 上gitlab 儀錶板
build 
build->plpelines