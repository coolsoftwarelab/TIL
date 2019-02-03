Git 환경설정 및 사용방법



1. 클라이언트



git 설치



bash실행 



$git init



$ ls -al



$git config --global user.name luckydog

$git config --global user.email luckydog@gmail.com



$git add test.txt (또는 git add . )

=>stage에 올린다



$git status

=>현재 상태



$git commit test.txt (또는 git commit . )

=>커밋



* add가 SVN에 add가 아니라 여기서는 stag에 올린다는 개념. 즉 최초 add 후에도 수정해서 commit 할때마다 add 후 commit 해야함 (강제로 commit 해도 먹긴 먹는듯)

프로젝트 첫 commit 에서는 반드시 메시지를 작성해야 함

vi 쓰듯이 메시지 작성 후 ":wq" 로 저장(commit)



* 영역

1) working copy = working directory = working tree : 작업 파일

2) stage = staging area = index : commit 대기를 하고 있는 파일이 있는 곳

3) repository = history = tree : commit 된 파일이 있는 곳



서버연결

$ git remote add origin ssh://git@172.111.0.111:22/home/git/AUTH_APP/

=>끝에 ' /  ' 붙일 것


$ git remote -v

origin  ssh://git@172.111.0.111:22/home/git/AUTH_APP/ (fetch)

origin  ssh://git@172.111.0.111:22/home/git/AUTH_APP/ (push)


$ git push

$ git push --set-upstream origin master

에러가 나면 퍼미션 체크 (임시)
chmod -R 777 *

서버에서 git log 로 정상적으로 업로드 됐는지 확인





Clone (checkout)

$ git clone ssh://git@172.111.0.111:22/home/git/AUTH_APP/ local_repo



$ git remote -v

=>연결 저장소 확인



태그

git tag -a v2 -m 'App store upload (versionCode 2)'




2. 서버



git 설치

$  git init --bare AUTH_APP

=>원격지 repo 생성




ㅁ 백업

1) 백업 : git bundle create repo_bundle HEAD master

2) 복원 : git clone repo_bundle repo





3. 사용방법



리비전 되돌리기



1) 동기화

git stash 



2) 리비전 되돌리기

- 최신버전에서 한단계 아래 리비전으로

git checkout HEAD~1



- 특정 리비전 (git log 상에 리비전 해시 값 ex) commit 8bdec996610736760fa248f77ebf09e2d3b3a2aa)

git checkout 8bdec996610736760fa248f77ebf09e2d3b3a2aa


3) 최신 리비전으로 복귀
git stash pop

* 안되면 강제로 master 로 복귀
git checkout -f master

* git stash 내역 삭제
git stash clear




특정파일 제외



프로젝트 메인에서

>vim .gitignore

구문 ex)

#built application files

*.apk

*.ap_

...



기존에 이미 commit 한 정보가 있다면,

git rm --cached [path]

ex)

>git rm -r --cached .



>git add .



>git commit -m ".gitignore policy added"



* mapping 정보 ignore에서 제외 명령어

$ git add -f app/build/outputs/mapping/release/

$ git commit .





브랜치 (Branch)

새로운 브랜치 생성하는 방법: $ git branch <BRANCHNAME>
브랜치 이동하는 방법: $ git checkout <BRANCHNAME>
브랜치 리스트 확인하기: $ git branch
브랜치 이름 변경하기: $ git branch -m <BRANCHNAME>
내가 작업하고 있는 브랜치 확인하기 $ git status
브랜치 삭제 : git branch -d <BRANCHNAME>




브랜치 합치기 (merge)

master 에 test_branch 합치기

>git checkout master

>git merge test_branch



* 머지 후 에러나는 부분 강제로 커밋하고 싶다면 '-i' 옵션 사용

>git add .

>git commit -i . -m 'force update'


Commit 취소

git reset HEAD^ : 최종 커밋을 취소. 워킹트리는 보존됨. (커밋은 했으나 push하지 않은 경우 유용)

git reset HEAD~2 : 마지막 2개의 커밋을 취소. 워킹트리는 보존됨.



Commit 메시지 변경

>git commit --amend





브랜치 덮어쓰기

second 브랜치를 master 에 덮어쓰고 싶은 경우 (force)



* 아래 명령을 수행하면 second 브랜치의 마지막 commit 시점 이후 혹은 다른 시점에

   master 브랜치에만 추가한 파일 또는 내역은 모두 삭제된다.



>git checkout master


>git reset --hard better_branch

optional)
git push -f origin master



브랜치 파일 공유 정보

브랜치는 각자의 파일정보를 가지고 있다.

ex) master, second 브랜치에 A, B파일이 있다면

master 브랜치에서 A파일을 지우거나 second 에서 C 파일을 추가해도 브랜치 간에 영향을 끼치지 않는다.

각 브랜치 checkout 해보면 타 브랜치에서 지우거나 추가한 내용은 반영 안됨 (git 폴더에도 현재 브랜치 파일들만 보임)
