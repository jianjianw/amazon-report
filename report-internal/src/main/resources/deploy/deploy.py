import subprocess, os, shutil
from sys import platform
import argparse, sys, datetime

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--dist")
    parser.add_argument("--config")
    parser.add_argument("--jar")
    py_path = sys.argv[0]
    args = parser.parse_args()
    if args.jar:
        jar = str(args.jar).replace('#', ' ')
    else:
        jar = "jar"
    if args.dist:
        dist = args.dist
    else:
        dist = None
    if args.config:
        config = args.config
    else:
        config = None
    if platform == "linux" or platform == "linux2":
        pass
    elif platform == "darwin":
        target_dir = '../../../../target/'
        if dist is None:
            dist = target_dir
        standard_dir = 'standard'
        platform_dirs = []
        for file in os.listdir('.'):
            if os.path.isdir(file):
                platform_dirs.append(file)
        if len(platform_dirs) == 0:
            exit(0)
        for dir in [standard_dir]+platform_dirs:
            if os.path.exists(target_dir+dir):
                shutil.rmtree(target_dir+dir)
        subprocess.run('''cd ../../../../target/;
        mkdir standard;
        cd standard;
        {} -xf ../amazon-report-internal-0.0.2-SNAPSHOT.war'''.format(jar), shell=True)
        for dir in platform_dirs:
            shutil.copytree(target_dir+standard_dir, target_dir+dir)
            for file in os.listdir(dir):
                shutil.copy2(dir+'/'+file, target_dir+dir+"/WEB-INF/classes/")
            subprocess.run('''ls -l;
            cd ../../../../target/{0}/;
            {1} -cvf amazonReport_{0}.war *;
            '''.format(dir, jar), shell=True)
            if os.path.exists(dist+"/amazonReport_{}.war".format(dir)):
                os.remove(dist+"/amazonReport_{}.war".format(dir))
            shutil.move(target_dir+dir+'/amazonReport_{}.war'.format(dir), dist)
            shutil.rmtree(target_dir+dir)
        shutil.rmtree(target_dir+standard_dir)
    elif platform == "win32":
        target_dir = '../../../../target/'
        target_dir = os.path.abspath(target_dir)+ '\\'
        deploy_path = os.path.dirname(py_path)
        os.chdir(deploy_path)
        if dist is None:
            dist = target_dir
        dist = dist + '\\{}-WEB-RELEASE'.format(datetime.date.today().isoformat())
        print(dist)
        if not os.path.exists(dist):
            os.mkdir(dist)
        standard_dir = 'standard'
        platform_dirs = []
        for file in os.listdir('.'):
            if os.path.isdir(file):
                platform_dirs.append(file)
        if len(platform_dirs) == 0:
            exit(0)
        for dir in [standard_dir]+platform_dirs:
            print(target_dir+dir)
            if os.path.exists(target_dir+dir):
                shutil.rmtree(target_dir+dir)
            if config is not None and os.path.exists(config+dir):
                shutil.rmtree(config+dir)
        subprocess.run('d:&&cd {}&&cd ../../../../target/&&mkdir standard&&cd standard&&{} -xf ../amazon-report-internal-0.0.2-SNAPSHOT.war'.format(deploy_path, jar),shell=True)
        print('d:&&cd {}&&cd ../../../../target/&&mkdir standard&&cd standard&&{} -xf ../amazon-report-internal-0.0.2-SNAPSHOT.war'.format(deploy_path, jar))
        for dir in platform_dirs:
            shutil.copytree(target_dir+standard_dir, target_dir+dir)
            if config is not None:
                shutil.copytree(dir, config+dir)
            for file in os.listdir(dir):
                shutil.copy2(dir+'/'+file, target_dir+dir+"/WEB-INF/classes/")
            subprocess.run('d:&&cd {2}&&cd ../../../../target/{0}/&&{1} -cvf amazonReport_{0}.war *'.format(dir, jar, deploy_path), shell=True,stdout=subprocess.PIPE)
            print('d:&&cd {2}&&cd ../../../../target/{0}/&&{1} -cvf amazonReport_{0}.war *'.format(dir, jar, deploy_path))
            if os.path.exists(dist+"/amazonReport_{}.war".format(dir)):
                os.remove(dist+"/amazonReport_{}.war".format(dir))
            shutil.move(target_dir+dir+'/amazonReport_{}.war'.format(dir), dist)
            shutil.rmtree(target_dir+dir)
        shutil.rmtree(target_dir+standard_dir)
