# -*- coding: utf-8 -*-
# Generated by Django 1.11.6 on 2017-10-28 20:50
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('group', '0002_remove_interestgroup_content_types'),
        ('content', '0004_remove_contenttype_components'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='content',
            name='content_type',
        ),
        migrations.DeleteModel(
            name='ContentType',
        ),
    ]
