/***************************************************************************
 *
 * Copyright © 2019 LT. All Rights Reserved.
 *
 ***************************************************************************/

/**
 *
 * @file: main.cpp
 *
 * @breaf: 
 *
 * @author: Lei Yunfei(towardstheway@gmail.com)
 *
 * @create: 2019/08/22 15时00分47秒
 *
 **/

#include <cstdio>
#include <cstdint>
#include <string>
#include <thread>

#include "singleton.h"


class Test {
public:
    void SetClsName(const std::string &name) {
        cls_name_ = name;
    }

    std::string GetClsName() const {
        return cls_name_;
    }

private:
    std::string cls_name_;
};


void thread_func() {
    printf("class-name: %s, addr: %p\n", Singleton<Test>::GetInstance().GetClsName().c_str(), &Singleton<Test>::GetInstance());
    printf("Set New Class Name\n");
    Singleton<Test>::GetInstancePtr()->SetClsName("TestNewName");
    printf("class-name: %s, addr: %p\n", Singleton<Test>::GetInstance().GetClsName().c_str(), &Singleton<Test>::GetInstance());
}

int main(int argc, char *argv[]) {
    Singleton<Test>::GetInstance().SetClsName("Test");
    printf("class-name: %s, addr: %p\n", Singleton<Test>::GetInstance().GetClsName().c_str(), &Singleton<Test>::GetInstance());
    std::thread t(thread_func);
    t.join();
    printf("class-name: %s, addr: %p\n", Singleton<Test>::GetInstance().GetClsName().c_str(), &Singleton<Test>::GetInstance());
    return 0;
}
