# OpenShift Cookbook - Chapter 5 Sample Application#

A simple Job portal written using Java EE 6 and PostgreSQL 9.2.

To run it on OpenShift, run the following command.

```
$ rhc app-create jobstore jbosseap postgresql-9.2 --from-code https://github.com/OpenShift-Cookbook/chapter5-jobstore-fileupload.git
```
