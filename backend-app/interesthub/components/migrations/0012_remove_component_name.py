# -*- coding: utf-8 -*-
# Generated by Django 1.11.6 on 2017-11-15 12:17
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('components', '0011_auto_20171114_2326'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='component',
            name='name',
        ),
    ]
