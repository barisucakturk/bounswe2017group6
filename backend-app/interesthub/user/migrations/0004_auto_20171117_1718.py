# -*- coding: utf-8 -*-
# Generated by Django 1.11.6 on 2017-11-17 14:18
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('admin', '0002_logentry_remove_auto_add'),
        ('group', '0006_auto_20171102_0131'),
        ('content', '0010_contenttype_component_names'),
        ('user', '0003_testuser'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='testuser',
            name='user_ptr',
        ),
        migrations.DeleteModel(
            name='TestUser',
        ),
    ]
