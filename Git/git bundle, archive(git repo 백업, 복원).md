## Git Bundle
### 1. 백업파일 생성

o 모든 브랜치
>git bundle create xxx.repo --all

o 특정 브랜치
>git bundle create xxx.repo HEAD xxx_branch

### 2. 백업파일 복원

>git clone xxx.repo ./repos
>git branch -a

<br>

## Git Archive

### 백업파일 생성
>git archive --format=zip xxx_branch -o xxx.zip

### 백업파일 복원
>단순 zip 파일이므로 압축해제 후 사용

<br>

## Git Archive vs Git Bundle
- git archive : 히스토리를 제거하고 소스코드만 백업할 때 사용 (.git을 제거하고 소스코드만 백업)
- git bundle : 히스토리를 포함한 모든 정보를 포함하여 단일 파일을 생성한다 (저장소 자체를 백업/복원할 때 사용)
