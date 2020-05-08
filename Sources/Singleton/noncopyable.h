/***************************************************************************
 *
 * Copyright © 2017 LT. All Rights Reserved.
 *
 ***************************************************************************/

/**
 *
 * @file: noncopyable.h
 *
 * @breaf: 基础类：禁止使用拷贝构造和复制操作函数来构造对象实例
 *
 * @author: Lei Yunfei(towardstheway@gmail.com)
 *
 * @create: 2017/03/21 21时10分46秒
 *
 **/

#ifndef  NONCOPYABLE_INC
#define  NONCOPYABLE_INC

namespace base {

class noncopyable_
{
    protected:
        noncopyable_() {}
        ~noncopyable_() {}

    private:
        noncopyable_(const noncopyable_ &);
        const noncopyable_ &operator = (const noncopyable_ &);
};

} // namespace base

typedef base::noncopyable_ noncopyable;

#endif   // ----- #ifndef NONCOPYABLE_INC  -----
