/***************************************************************************
 *
 * Copyright © 2019 LT. All Rights Reserved.
 *
 ***************************************************************************/

/**
 *
 * @file: singleton.h
 *
 * @breaf: 多线程下的单例模式
 *
 * @author: Lei Yunfei(towardstheway@gmail.com)
 *
 * @create: 2019/08/22 10时08分55秒
 *
 **/

#include <cassert>
#include <pthread.h>
#include "noncopyable.h"


template<typename T>
class Singleton : noncopyable {
public:
    Singleton() = delete;
    ~Singleton() = delete;

    static T &GetInstance();
    static T *GetInstancePtr();

private:
    static void init();
    static void destroy();

    static T *instance_;
    static pthread_once_t thread_once_;
};

template<typename T>
T &Singleton<T>::GetInstance() {
    pthread_once(&thread_once_, &Singleton<T>::init);
    assert(instance_ != nullptr);
    return *instance_;
}


template<typename T>
T *Singleton<T>::GetInstancePtr() {
    pthread_once(&thread_once_, &Singleton<T>::init);
    assert(instance_ != nullptr);
    return instance_;
}

template<typename T>
void Singleton<T>::init() {
    instance_ = new T();
     // ObjectManager::AtExit(destory)
}

template<typename T>
void Singleton<T>::destroy() {
    if (instance_) {
        delete instance_;
    }
    instance_ = nullptr;
}

template<typename T>
T *Singleton<T>::instance_ = nullptr;

template<typename T>
pthread_once_t Singleton<T>::thread_once_ = PTHREAD_ONCE_INIT;

